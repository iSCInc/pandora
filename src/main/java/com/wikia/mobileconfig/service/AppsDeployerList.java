package com.wikia.mobileconfig.service;

import com.wikia.mobileconfig.MobileConfigConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AppsDeployerList implements AppsListService {
    private static final String APP_DEPLOYER_HEALTH_CHECK_URL = "http://apps-deployer-panel-s1/api/";
    private static final String APPS_DEPLOYER_LIST_URL = "http://apps-deployer-panel-s1/api/app-configuration/";
    private final HttpClient httpClient;

    public AppsDeployerList(Environment environment, MobileConfigConfiguration configuration) {
        this.httpClient = new HttpClientBuilder(environment)
            .using(configuration.getHttpClientConfiguration())
            .build("apps-deployer-list-service");
    }

    private List<HashMap<String, Object>> getAppsList() throws IOException {
        Optional<String> response = this.executeHttpRequest(APPS_DEPLOYER_LIST_URL);
        ObjectMapper mapper = new ObjectMapper();
        if (response.isPresent()) {
            return mapper.readValue(response.get(), new TypeReference<List<HashMap<String, Object>>>() {});
        } else {
            throw new IllegalStateException(
                String.format("Error, the response for %s is not valid!", APPS_DEPLOYER_LIST_URL)
            );
        }
    }

    private Optional<String> executeHttpRequest(final String requestUrl) throws IOException {
        HttpGet httpGet = new HttpGet(requestUrl);
        ResponseHandler<String> responseHandler = new StringResponseHandler(requestUrl);
        return Optional.of(this.httpClient.execute(httpGet, responseHandler));
    }

    @Override
    public Boolean isValidAppTag(String appTag) throws IOException {
        List<HashMap<String, Object>> list = this.getAppsList();
        return list.stream().filter(item -> item.values().contains(appTag)).findFirst().isPresent();
    }

    @Override
    public Boolean isUp() throws IOException {
        try {
            this.executeHttpRequest(APP_DEPLOYER_HEALTH_CHECK_URL);
            return true;
        } catch (ClientProtocolException exception) {
            return false;
        }
    }

    private static class StringResponseHandler implements ResponseHandler<String> {
        private final String requestUrl;

        public StringResponseHandler(String requestUrl) {
            this.requestUrl = requestUrl;
        }

        public String handleResponse(final HttpResponse response) throws IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                Optional<HttpEntity> entity = Optional.of(response.getEntity());
                if (!entity.isPresent()) {
                    throw new ClientProtocolException(
                        String.format("Empty response body from '%s'!", requestUrl)
                    );
                }
                return EntityUtils.toString(entity.get());
            } else {
                throw new ClientProtocolException(
                    String.format("Unexpected response status %d from '%s'.", status, requestUrl)
                );
            }
        }
    }
}
