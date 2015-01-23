package com.wikia.mobileconfig.service;

import com.wikia.mobileconfig.MobileConfigConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.common.base.Optional;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AppsDeployerList implements AppsListService {

  private static final String APPS_DEPLOYER_HEALTH_CHECK_URL_FORMAT = "http://%s/api/";
  private static final String APPS_DEPLOYER_LIST_URL_FORMAT = "http://%s/api/app-configuration/";

  private static String appsDeployerDomain;

  private static final String
      APPS_LIST_RESPONSE_ERROR_FORMAT = "Error, the response for %s is not valid!";

  private final HttpClient httpClient;

  public AppsDeployerList(Environment environment, MobileConfigConfiguration configuration) {
    this.httpClient = new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build("apps-deployer-list-service");

    this.appsDeployerDomain = configuration.getAppsDeployerDomain();
  }

  private List<HashMap<String, Object>> getAppsList() throws IOException {
    String appsDeployerUrl = String.format(APPS_DEPLOYER_LIST_URL_FORMAT, appsDeployerDomain);
    Optional<String> response = this.executeHttpRequest(appsDeployerUrl);
    ObjectMapper mapper = new ObjectMapper();

    if (response.isPresent()) {
      return mapper.readValue(response.get(), new TypeReference<List<HashMap<String, Object>>>() {
      });
    } else {
      throw new IllegalStateException(
          String.format(APPS_LIST_RESPONSE_ERROR_FORMAT, appsDeployerUrl)
      );
    }
  }

  private Optional<String> executeHttpRequest(final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
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
      String appsDeployerHealthCheckUrl = String.format(
          APPS_DEPLOYER_HEALTH_CHECK_URL_FORMAT,
          appsDeployerDomain
      );
      this.executeHttpRequest(appsDeployerHealthCheckUrl);

      return true;
    } catch (ClientProtocolException exception) {
      return false;
    }
  }
}
