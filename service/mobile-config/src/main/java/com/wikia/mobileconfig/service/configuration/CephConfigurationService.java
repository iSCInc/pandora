package com.wikia.mobileconfig.service.configuration;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * A class responsible for getting mobile applications' configuration from our Ceph buckets via
 * HTTP
 */
public class CephConfigurationService extends ConfigurationServiceBase {

  private static final String
      CEPH_URL_FORMAT = "http://%s:%s/mobile-configuration-service/%s/%s/config.json";

  private final ObjectMapper mapper;
  private final String cephDomain;
  private final String cephPort;
  @Inject
  @Named("http-configuration-service")
  private HttpClient httpClient;

  @Inject
  public CephConfigurationService(MobileConfigConfiguration configuration) {
    this(configuration.getCephDomain(),
         configuration.getCephPort());
  }

  public CephConfigurationService(HttpClient httpClient, String cephDomain, String cephPort) {
    this(cephDomain, cephPort);
    this.httpClient = httpClient;
  }

  public CephConfigurationService(String cephDomain, String cephPort) {
    this.mapper = new ObjectMapper();
    this.cephDomain = cephDomain;
    this.cephPort = cephPort;
  }

  @Override
  public MobileConfiguration getDefault(String platform) throws IOException {
    try {
      return this.mapper.readValue(
          this.executeHttpRequest(createCephUrl(platform, "_default")),
          MobileConfiguration.class
      );
    } catch (IOException e) {
      MobileConfigApplication.LOGGER.info(
          String.format(CONFIGURATION_NOT_FOUND_DEBUG_MESSAGE_FORMAT, platform), e
      );
      return new EmptyMobileConfiguration();
    }
  }

  @Override
  public MobileConfiguration getConfiguration(String platform, String appTag, String uiLang,
                                              String contentLang) throws IOException {

    MobileConfiguration configuration = null;

    try {
      configuration = this.mapper.readValue(
          this.executeHttpRequest(createCephUrl(platform, appTag)),
          MobileConfiguration.class
      );
    } catch (IOException e) {
      MobileConfigApplication.LOGGER.info(
          String.format(CONFIGURATION_FOR_APP_TAG_NOT_FOUND_DEBUG_MESSAGE_FORMAT, appTag, platform),
          e
      );

      configuration = getDefault(platform);
    }

    translateConfiguration(configuration, uiLang);
    return configuration;
  }

  private String createCephUrl(String platform, String appTag) {
    return String.format(CEPH_URL_FORMAT, cephDomain, cephPort, platform, appTag);
  }

  private String executeHttpRequest(final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    return this.httpClient.execute(httpGet, responseHandler);
  }
}
