package com.wikia.mobileconfig.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.gateway.AppsDeployerList;
import com.wikia.mobileconfig.service.HttpConfigurationService;
import com.wikia.pandora.test.FastTest;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import io.dropwizard.testing.junit.ResourceTestRule;

import static org.fest.assertions.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link MobileConfigResource}.
 */
@Category(FastTest.class)
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
    when(appsListMock.isValidAppTag("test-platform", "test-app")).thenReturn(false);

    try {
      resources.client()
          .target("/configurations/platform/test-platform/app/test-app")
          .request()
          .get(String.class);
      fail("404 - missing invalid appTag exception");
    } catch (BadRequestException ex) {
      // safely ignore
    }
    verify(appsListMock, times(1)).isValidAppTag("test-platform", "test-app");
    verify(httpServiceMock, never()).getConfiguration("test-platform", "test-app", "en-us", "en-us");
    reset(appsListMock, httpServiceMock);
  }

  @Test
  public void getMobileApplicationConfigFails_notFound() throws IOException {
    when(appsListMock.isValidAppTag("test-platform", "test-app")).thenReturn(true);
    when(httpServiceMock.getConfiguration("test-platform", "test-app", "en-us", "en-us"))
        .thenReturn(new EmptyMobileConfiguration());
    when(httpServiceMock.createSelfUrl("test-platform", "test-app"))
        .thenReturn("/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us");

    try {
      resources.client()
          .target(
              "/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us")
          .request()
          .get(String.class);
      fail("404 - missing invalid modules exception");
    } catch (NotFoundException ex) {
      // safely ignore
    }
    verify(appsListMock).isValidAppTag("test-platform", "test-app");
    verify(httpServiceMock).getConfiguration("test-platform", "test-app", "en-us", "en-us");
    verify(httpServiceMock, never()).getDefault("test-platform");
    reset(appsListMock, httpServiceMock);
  }

  @Test
  public void getMobileApplicationConfigSuccess() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    MobileConfiguration cfgMock = mapper.readValue(
        new File("src/test/resources/fixtures/test-platform:test-app.json"),
        MobileConfiguration.class
    );

    when(appsListMock.isValidAppTag("test-platform", "test-app")).thenReturn(true);
    when(httpServiceMock.createSelfUrl("test-platform", "test-app"))
      .thenReturn("/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us");
    when(httpServiceMock.getConfiguration("test-platform", "test-app", "en-us", "en-us"))
        .thenReturn(cfgMock);

    String response = resources.client()
        .target(
            "/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us")
        .request()
        .get(String.class);

    assert(response).contains("modules");

    verify(appsListMock).isValidAppTag("test-platform", "test-app");
    verify(httpServiceMock).getConfiguration("test-platform", "test-app", "en-us", "en-us");
    verify(httpServiceMock, never()).getDefault("test-platform");
    reset(appsListMock, httpServiceMock);
  }
}
