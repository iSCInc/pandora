package com.wikia.mobileconfig.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.UniformInterfaceException;

import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.service.AppsDeployerList;
import com.wikia.mobileconfig.service.HttpConfigurationService;

import io.dropwizard.testing.junit.ResourceTestRule;

import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link MobileConfigResource}.
 */
public class MobileConfigResourceTest {

  private static final AppsDeployerList appsListMock = mock(AppsDeployerList.class);
  private static final HttpConfigurationService httpServiceMock = mock(
      HttpConfigurationService.class
  );

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new MobileConfigResource(httpServiceMock, appsListMock)).build();

  @Test
  public void getMobileApplicationConfigFails_invalidAppTag() throws IOException {
    when(appsListMock.isValidAppTag("test-app")).thenReturn(false);

    try {
      resources.client()
          .resource("/configurations/platform/test-platform/app/test-app")
          .get(String.class);
      fail("404 - missing invalid appTag exception");
    } catch (Exception e) {
      assert(e instanceof UniformInterfaceException);
    }

    verify(appsListMock, times(1)).isValidAppTag("test-app");
    verify(httpServiceMock, never()).getConfiguration("test-platform", "test-app");
    reset(appsListMock, httpServiceMock);
  }

  @Test
  public void getMobileApplicationConfigFails_notFound() throws IOException {
    when(appsListMock.isValidAppTag("test-app")).thenReturn(true);
    when(httpServiceMock.getConfiguration("test-platform", "test-app"))
        .thenReturn(new EmptyMobileConfiguration());
    when(httpServiceMock.createSelfUrl("test-platform", "test-app"))
        .thenReturn("/configurations/platform/test-platform/app/test-app");

    try {
      resources.client()
        .resource("/configurations/platform/test-platform/app/test-app")
        .get(String.class);
      fail("404 - missing invalid modules exception");
    } catch (Exception e) {
      assert(e instanceof UniformInterfaceException);
    }

    verify(appsListMock, times(1)).isValidAppTag("test-app");
    verify(httpServiceMock, times(1)).getConfiguration("test-platform", "test-app");
    reset(appsListMock, httpServiceMock);
  }
}
