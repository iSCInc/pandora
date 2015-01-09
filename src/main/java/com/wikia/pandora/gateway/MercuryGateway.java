package com.wikia.pandora.gateway;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MercuryGateway {

    public static String DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT = "http://%s.wikia.com/api/v1/Mercury/Article?title=%s";

    private final HttpClient httpClient;
    private final String articleRequestFormat;

    public MercuryGateway(HttpClient httpClient, String articleRequestFormat) {
        this.httpClient = httpClient;
        this.articleRequestFormat = articleRequestFormat;
    }

    public MercuryGateway(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.articleRequestFormat = MercuryGateway.DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT;
    }

    public Map<String, Object> getArticle(String wikia, String title) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Optional<String> response = this.getRequestHandler(formatMercuryRequest(wikia, title));
        Map<String, Object> article = mapper.readValue(response.get(), new TypeReference<HashMap<String, Object>>(){});;
        return article;
    }

    public String formatMercuryRequest(String wikia, String title) {
        return formatMercuryRequest(wikia, title, this.articleRequestFormat);
    }

    public String formatMercuryRequest(String wikia, String title, String format) {
        try {
            return String.format(format, wikia, URLEncoder.encode(title, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return String.format(format, wikia, title);
        }
    }

    public Optional<String> getRequestHandler(final String requestUrl) throws IOException {
        HttpGet httpGet = new HttpGet(requestUrl);
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            public String handleResponse(final HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    Optional<HttpEntity> entity = Optional.of(response.getEntity());
                    if (!entity.isPresent()) {
                        throw new ClientProtocolException(String.format("Empty response body from '%s'!", requestUrl));
                    }
                    return EntityUtils.toString(entity.get());
                } else {
                    throw new ClientProtocolException(
                            String.format("Unexpected response status %d from '%s'.", status, requestUrl));
                }
            }

        };

        return Optional.of(this.httpClient.execute(httpGet, responseHandler));
    }

}
