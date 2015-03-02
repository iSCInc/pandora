package com.wikia.mobileconfig.gateway;

import com.google.common.base.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

public class AppsDeployerListContainer implements AppsListService {

  public static final int DEFAULT_CACHE_TIME_IN_SEC = 3600;
  private static final String APPS_DEPLOYER_HEALTH_CHECK_URL_FORMAT = "http://%s/api/";
  private static final String APPS_DEPLOYER_LIST_URL_FORMAT = "http://%s/api/app-configuration/";
  private static final String
      APPS_LIST_RESPONSE_ERROR_FORMAT = "Error, the response for %s is not valid!";
  //How long to cache the result. Set to 0 to disable caching.
  private final int cacheTimeInSec;
  private final HttpClient httpClient;
  private final String appsDeployerDomain;
  private List<HashMap<String, Object>> appsList;

  private Date appsListUpdateTime = new Date();

  public AppsDeployerListContainer(HttpClient httpClient, String appsDeployerDomain) {
    this.httpClient = httpClient;

    this.appsDeployerDomain = appsDeployerDomain;
    this.cacheTimeInSec = DEFAULT_CACHE_TIME_IN_SEC;
  }

  public AppsDeployerListContainer(Environment environment, MobileConfigConfiguration configuration) {
    this.httpClient = new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build("apps-deployer-list-service");

    this.appsDeployerDomain = configuration.getAppsDeployerDomain();
    this.cacheTimeInSec = configuration.getCacheTime();
  }

  private List<HashMap<String, Object>> requestAppsList() throws IOException {
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

  private synchronized List<HashMap<String, Object>> getAppListSync() throws IOException {

    long
        diffInSeconds =
        TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - appsListUpdateTime.getTime());
    if (appsList == null || diffInSeconds > cacheTimeInSec) {
      appsList = requestAppsList();
      appsListUpdateTime = new Date();
    }

    return appsList;
  }

  private Optional<String> executeHttpRequest(final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    String response = this.httpClient.execute(httpGet, responseHandler);
    return Optional.of(response);
  }

  @Override
  public boolean isValidAppTag(String platform, String appTag) throws IOException {
    List<HashMap<String, Object>> list = this.getAppList(platform);
    return list.stream().filter(item -> item.values().contains(appTag)).findFirst().isPresent();
  }

  @Override
  public List<HashMap<String, Object>> getAppList(String platform) throws IOException {
    if (isUsingCache()) {
      return requestAppsList();
    } else {
      return getAppListSync();
    }
  }

  private boolean isUsingCache() {
    return cacheTimeInSec <= 0;
  }

  @Override
  public boolean isUp() throws IOException {
    Boolean result = false;

    try {
      String appsDeployerHealthCheckUrl = String.format(
          APPS_DEPLOYER_HEALTH_CHECK_URL_FORMAT,
          appsDeployerDomain
      );
      this.executeHttpRequest(appsDeployerHealthCheckUrl);

      result = true;
    } catch (ClientProtocolException exception) {
      MobileConfigApplication.LOGGER.error(
          "Apps deployer host is unreachable", exception
      );
    }

    return result;
  }
}
