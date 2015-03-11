package com.wikia.mobileconfig.service.application;

import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.mobileconfig.exceptions.CephAppsListServiceException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class CephAppsListService implements AppsListService {

  private static final String
      CEPH_URL_FORMAT = "http://%s:%s/mobile-configuration-service-apps-list/%s";
  private static final Logger LOGGER = LoggerFactory.getLogger(CephAppsListService.class);
  private final String cephDomain;
  private final String cephPort;
  private final ObjectMapper mapper;
  @Inject
  @Named("apps-deployer-list-service")
  private HttpClient httpClient;

  @Inject
  public CephAppsListService(MobileConfigConfiguration configuration) {
    this(configuration.getCephDomain(), configuration.getCephPort());
  }

  public CephAppsListService(HttpClient client, String cephDomain, String cephPort) {
    this(cephDomain, cephPort);
    this.httpClient = client;
  }

  public CephAppsListService(String cephDomain, String cephPort) {
    this.cephDomain = cephDomain;
    this.cephPort = cephPort;
    this.mapper = new ObjectMapper();
  }

  @Override
  public boolean isValidAppTag(String platform, String appTag) throws IOException {
    List<HashMap<String, Object>> list = getAppListFromCeph(platform);
    return list.stream().filter(item -> item.values().contains(appTag)).findFirst().isPresent();
  }

  @Override
  public List<HashMap<String, Object>> getAppList(String platform) throws IOException {
    return getAppListFromCeph(platform);
  }

  private List<HashMap<String, Object>> getAppListFromCeph(String platform) {
    String cephUrl = String.format(CEPH_URL_FORMAT, cephDomain, cephPort, platform);
    HttpGet httpGet = new HttpGet(cephUrl);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    try {
      String listResponse = this.httpClient.execute(httpGet, responseHandler);
      return mapper.readValue(listResponse, new TypeReference<List<HashMap<String, Object>>>() {
      });
    } catch (NullPointerException | HttpResponseException e) {
      LOGGER.error("Cannot get app list from ceph", e);
      throw new CephAppsListServiceException("No data", e.getMessage());
    } catch (JsonProcessingException e) {
      throw new CephAppsListServiceException("Not valid file format",
                                             "File format is not valid, and could not be parsed");
    } catch (IOException e) {
      throw new CephAppsListServiceException("Not expected exception", e.getMessage());
    }
  }

  @Override
  public boolean isUp() throws IOException {
    boolean isUp = false;
    String cephUrl = String.format("http://%s:%s/", cephDomain, cephPort);
    HttpGet httpGet = new HttpGet(cephUrl);
    try {
      HttpResponse response = this.httpClient.execute(httpGet);
      isUp = response.getStatusLine().getStatusCode() == 200;
    } catch (IOException e) {
      LOGGER.error("Ceph is unavaible", e);
    }
    return isUp;
  }
}
