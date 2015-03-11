package com.wikia.mobileconfig.service.application;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
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

public class AppsDeployerListContainer implements AppsListService {

  public static final int DEFAULT_CACHE_TIME_IN_SEC = 3600;
  private static final String APPS_DEPLOYER_HEALTH_CHECK_URL_FORMAT = "http://%s/api/";
  private static final String APPS_DEPLOYER_LIST_URL_FORMAT = "http://%s/api/app-configuration/";
  private static final String
      APPS_LIST_RESPONSE_ERROR_FORMAT = "Error, the response for %s is not valid!";
  //How long to cache the result. Set to 0 to disable caching.
  private final int cacheTimeInSec;
  private final String appsDeployerDomain;
  @Inject
  @Named("apps-deployer-list-service")
  private HttpClient httpClient;
  private List<HashMap<String, Object>> appsList;

  private Date appsListUpdateTime = new Date();
  private ObjectMapper objectMapper;

  public AppsDeployerListContainer(HttpClient httpClient, String appsDeployerDomain) {
    this(httpClient, appsDeployerDomain, DEFAULT_CACHE_TIME_IN_SEC);
  }

  @Inject
  public AppsDeployerListContainer(MobileConfigConfiguration configuration) {
    this(configuration.getAppsDeployerDomain(),
         configuration.getCacheTime());
  }

  public AppsDeployerListContainer(HttpClient httpClient, String appsDeployerDomain,
                                   int cacheTimeInSec) {
    this(appsDeployerDomain, cacheTimeInSec);
    this.httpClient = httpClient;
  }

  public AppsDeployerListContainer(String appsDeployerDomain, int cacheTimeInSec) {
    this.appsDeployerDomain = appsDeployerDomain;
    this.cacheTimeInSec = cacheTimeInSec;
  }

  private List<HashMap<String, Object>> requestAppsList() throws IOException {
    String appsDeployerUrl = String.format(APPS_DEPLOYER_LIST_URL_FORMAT, appsDeployerDomain);
    Optional<String> response = this.executeHttpRequest(appsDeployerUrl);
    objectMapper = new ObjectMapper();

    if (response.isPresent()) {
      return objectMapper
          .readValue(response.get(), new TypeReference<List<HashMap<String, Object>>>() {
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
    return Optional.fromNullable(response);
  }

  @Override
  public boolean isValidAppTag(String platform, String appTag) throws IOException {
    List<HashMap<String, Object>> list = this.getAppList(platform);
    return list.stream().filter(item -> item.values().contains(appTag)).findFirst().isPresent();
  }

  @Override
  public List<HashMap<String, Object>> getAppList(String platform) throws IOException {
    if (isUsingCache()) {
      return getAppListSync();
    } else {
      return requestAppsList();
    }
  }

  private boolean isUsingCache() {
    return cacheTimeInSec > 0;
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
