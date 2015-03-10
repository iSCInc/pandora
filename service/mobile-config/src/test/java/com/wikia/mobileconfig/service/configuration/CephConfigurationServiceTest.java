package com.wikia.mobileconfig.service.configuration;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.service.configuration.CephConfigurationService;
import com.wikia.pandora.core.testhelper.TestHelper;

import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CephConfigurationServiceTest {
  private HttpClient httpClient;
  private String cephDomain = "ceph-domain";
  private String cephPort = "80";
  private CephConfigurationService configService;

  @Before
  public void initialize() {
    httpClient = mock(HttpClient.class);
    configService = new CephConfigurationService(httpClient, cephDomain, cephPort);
  }

  @Test
  public void getEmptyConfiguration() throws Exception {
    TestHelper.addMockRequestException(httpClient, IOException.class);
    MobileConfiguration
        config = configService.getConfiguration("testPlatform", "testTag", "xx", "xx");
    assertThat(config).isExactlyInstanceOf(EmptyMobileConfiguration.class);
  }

  @Test
  public void getDefaultConfiguration() throws Exception {
    String configContent = new String(Files.readAllBytes(
            Paths.get("src/test/resources/fixtures/test-platform_test-app.json")));
    TestHelper.addMockRequest(httpClient, configContent);
    MobileConfiguration config = configService.getDefault("testPlatform");
    assertThat(config).isExactlyInstanceOf(MobileConfiguration.class);
  }
}
