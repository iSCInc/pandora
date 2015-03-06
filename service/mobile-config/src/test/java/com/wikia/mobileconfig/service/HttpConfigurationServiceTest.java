package com.wikia.mobileconfig.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class HttpConfigurationServiceTest {
  private HttpClient httpClient;
  private ObjectMapper objectMapper;
  private String cephDomain = "ceph-domain";
  private String cephPort = "80";
  private HttpConfigurationService configService;

  @Before
  public void initialize() {
    httpClient = mock(HttpClient.class);
    objectMapper = mock(ObjectMapper.class);
    configService = new HttpConfigurationService(httpClient, objectMapper, cephDomain, cephPort);
  }

  @Test
  public void getEmptyConfiguration() throws Exception {
    when(httpClient.execute(any(HttpGet.class), any(BasicResponseHandler.class))).thenThrow(IOException.class);
    MobileConfiguration
        config = configService.getConfiguration("testPlatform", "testTag", "xx", "xx");
    assertThat(config).isExactlyInstanceOf(EmptyMobileConfiguration.class);
  }

  @Test
  public void getDefaultConfiguration() throws Exception {
    MobileConfiguration cfgMock = new ObjectMapper().readValue(
        new File("src/test/resources/fixtures/test-platform:test-app.json"),
        MobileConfiguration.class
    );

    when(objectMapper.readValue(anyString(), MobileConfiguration.class)).thenReturn(cfgMock);
    MobileConfiguration config = configService.getDefault("testPlatform");
    assertThat(config).isExactlyInstanceOf(MobileConfiguration.class);
  }
}
