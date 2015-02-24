package com.wikia.mobileconfig.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.gateway.AppsDeployerList;
import com.wikia.mobileconfig.service.HttpConfigurationService;

import org.junit.ClassRule;
import org.junit.Test;

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
public class MobileConfigResourceTest {

  private static final AppsDeployerList APPS_LIST_MOCK = mock(AppsDeployerList.class);
  private static final HttpConfigurationService HTTP_SERVICE_MOCK = mock(
      HttpConfigurationService.class
  );

  @ClassRule
  public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
      .addResource(new MobileConfigResource(HTTP_SERVICE_MOCK, APPS_LIST_MOCK)).build();

  @Test
  public void getMobileApplicationConfigFails_invalidAppTag() throws IOException {
    when(APPS_LIST_MOCK.isValidAppTag("test-platform", "test-app")).thenReturn(false);

    try {
      RESOURCES.client()
          .target("/configurations/platform/test-platform/app/test-app")
          .request()
          .get(String.class);
      fail("404 - missing invalid appTag exception");
    } catch (BadRequestException ex) {
      // safely ignore
    }
    verify(APPS_LIST_MOCK, times(1)).isValidAppTag("test-platform", "test-app");
    verify(HTTP_SERVICE_MOCK, never()).getConfiguration("test-platform", "test-app", "en-us", "en-us");
    reset(APPS_LIST_MOCK, HTTP_SERVICE_MOCK);
  }

  @Test
  public void getMobileApplicationConfigFails_notFound() throws IOException {
    when(APPS_LIST_MOCK.isValidAppTag("test-platform", "test-app")).thenReturn(true);
    when(HTTP_SERVICE_MOCK.getConfiguration("test-platform", "test-app", "en-us", "en-us"))
        .thenReturn(new EmptyMobileConfiguration());
    when(HTTP_SERVICE_MOCK.createSelfUrl("test-platform", "test-app"))
        .thenReturn(
            "/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us");

    try {
      RESOURCES.client()
          .target(
              "/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us")
          .request()
          .get(String.class);
      fail("404 - missing invalid modules exception");
    } catch (NotFoundException ex) {
      // safely ignore
    }
    verify(APPS_LIST_MOCK).isValidAppTag("test-platform", "test-app");
    verify(HTTP_SERVICE_MOCK).getConfiguration("test-platform", "test-app", "en-us", "en-us");
    verify(HTTP_SERVICE_MOCK, never()).getDefault("test-platform");
    reset(APPS_LIST_MOCK, HTTP_SERVICE_MOCK);
  }

  @Test
  public void getMobileApplicationConfigSuccess() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    MobileConfiguration cfgMock = mapper.readValue(
        new File("src/test/resources/fixtures/test-platform:test-app.json"),
        MobileConfiguration.class
    );

    when(APPS_LIST_MOCK.isValidAppTag("test-platform", "test-app")).thenReturn(true);
    when(HTTP_SERVICE_MOCK.createSelfUrl("test-platform", "test-app"))
      .thenReturn(
          "/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us");
    when(HTTP_SERVICE_MOCK.getConfiguration("test-platform", "test-app", "en-us", "en-us"))
        .thenReturn(cfgMock);

    String response = RESOURCES.client()
        .target(
            "/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us")
        .request()
        .get(String.class);

    assert(response).contains("modules");

    verify(APPS_LIST_MOCK).isValidAppTag("test-platform", "test-app");
    verify(HTTP_SERVICE_MOCK).getConfiguration("test-platform", "test-app", "en-us", "en-us");
    verify(HTTP_SERVICE_MOCK, never()).getDefault("test-platform");
    reset(APPS_LIST_MOCK, HTTP_SERVICE_MOCK);
  }
}
