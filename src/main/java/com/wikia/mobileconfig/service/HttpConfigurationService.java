package com.wikia.mobileconfig.service;

import com.google.common.base.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

/**
 * A class responsible for getting mobile applications' configuration from our Ceph buckets via HTTP
 */
public class HttpConfigurationService extends ConfigurationServiceBase {

  private static final String
      CEPH_URL_FORMAT =
      "http://public.nandy.wikia-dev.com/public/mobile-configuration-service/%s/%s/config.json";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;

  public HttpConfigurationService(
      Environment environment,
      MobileConfigConfiguration configuration
  ) {
    this.httpClient = new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build("http-configuration-service");
    this.mapper = new ObjectMapper();
  }

  @Override
  public MobileConfiguration getDefault(String platform) throws IOException {
    try {
      return this.mapper.readValue(
          this.executeHttpRequest(String.format(CEPH_URL_FORMAT, platform, "_default")),
          MobileConfiguration.class
      );
    } catch (IOException e) {
      return new EmptyMobileConfiguration();
    }
  }

  @Override
  public MobileConfiguration getConfiguration(String platform, String appTag) throws IOException {
    try {
      MobileConfiguration configuration = this.mapper.readValue(
          this.executeHttpRequest(String.format(CEPH_URL_FORMAT, platform, appTag)),
          MobileConfiguration.class
      );

      return configuration;
    } catch (IOException e) {
      MobileConfigApplication.logger.info(
          String.format(CONFIGURATION_NOT_FOUND_DEBUG_MESSAGE_FORMAT, appTag, platform)
      );

      return getDefault(platform);
    }
  }

  private String executeHttpRequest(final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    return this.httpClient.execute(httpGet, responseHandler);
  }
}
