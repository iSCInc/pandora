package com.wikia.mobileconfig.gateway;

import com.google.common.base.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.vignette.UrlConfig;
import com.wikia.vignette.UrlGenerator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

public class AppsDeployerList implements AppsListService {

  private static final String APPS_DEPLOYER_HEALTH_CHECK_URL_FORMAT = "http://%s/api/";
  private static final String APPS_DEPLOYER_LIST_URL_FORMAT = "http://%s/api/app-configuration/";

  //How long to cache the result. Set to 0 to disable caching.
  private static final int CACHE_RESULT_SEC = 0;

  private static String appsDeployerDomain;

  private static final String
      APPS_LIST_RESPONSE_ERROR_FORMAT = "Error, the response for %s is not valid!";

  private final HttpClient httpClient;

  private List<HashMap<String, Object>> appsList;

  private Date appsListUpdateTime = new Date();

  public AppsDeployerList(HttpClient httpClient, String appsDeployerDomain) {
    this.httpClient = httpClient;

    AppsDeployerList.appsDeployerDomain = appsDeployerDomain;
  }

  public AppsDeployerList(Environment environment, MobileConfigConfiguration configuration) {
    this.httpClient = new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build("apps-deployer-list-service");

    appsDeployerDomain = configuration.getAppsDeployerDomain();
  }

  private List<HashMap<String, Object>> requestAppsList() throws IOException {
    String appsDeployerUrl = String.format(APPS_DEPLOYER_LIST_URL_FORMAT, appsDeployerDomain);
    Optional<String> response = this.executeHttpRequest(appsDeployerUrl);
    ObjectMapper mapper = new ObjectMapper();

    if (response.isPresent()) {
      List<HashMap<String, Object>> appsList = mapper.readValue(
          response.get(),
          new TypeReference<List<HashMap<String, Object>>>() {});

      appsList.forEach(app -> {
        List<HashMap<String, Object>> images = (List<HashMap<String, Object>>) app.get("vignetteimage_set");
        HashMap<String, HashMap<String, String>> appImages = new HashMap<>();

        images.forEach(data -> {
          String bucket = (String) data.get("bucket");
          String type = (String) data.get("type");
          String path = (String) data.get("path");
          int timestamp = (int) data.get("timestamp");

          appImages.put(type, generateVignetteImageUrls(bucket, path, timestamp));
        });

        app.put("images", appImages);
      });

      return appsList;
    } else {
      throw new IllegalStateException(
          String.format(APPS_LIST_RESPONSE_ERROR_FORMAT, appsDeployerUrl)
      );
    }
  }

  private synchronized List<HashMap<String, Object>> getAppListSync() throws IOException {

    long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - appsListUpdateTime.getTime());
    if (appsList == null || diffInSeconds > CACHE_RESULT_SEC) {
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
    if (CACHE_RESULT_SEC <= 0) {
      return requestAppsList();
    } else {
      return getAppListSync();
    }
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

  protected HashMap<String, String> generateVignetteImageUrls(String bucket, String path, int timestamp) {
    HashMap<String, String> urls = new HashMap<>();
    UrlConfig config = new UrlConfig.Builder()
        .bucket(bucket)
        .timestamp(timestamp) // todo: this isn't being added. fixed?
        .relativePath(path)
        .build();

    try {
      String original = new UrlGenerator.Builder()
          .config(config)
          .build()
          .url();

      urls.put("original", original);
    } catch (URISyntaxException e) {
      LoggerFactory.getLogger(AppsDeployerList.class).error("error generating urls", e);
    }

    return urls;
  }
}
