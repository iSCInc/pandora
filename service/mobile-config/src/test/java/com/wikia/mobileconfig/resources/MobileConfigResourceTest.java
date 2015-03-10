package com.wikia.mobileconfig.resources;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.service.application.AppsDeployerListContainer;
import com.wikia.mobileconfig.service.configuration.CephConfigurationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 * Unit tests for {@link MobileConfigResource}.
 */
public class MobileConfigResourceTest {

  private static final AppsDeployerListContainer APPS_LIST_MOCK = mock(AppsDeployerListContainer.class);
  private static final CephConfigurationService HTTP_SERVICE_MOCK = mock(
      CephConfigurationService.class
  );

  @Rule
  public final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new MobileConfigResource(HTTP_SERVICE_MOCK, APPS_LIST_MOCK)).build();

  @Test(expected=BadRequestException.class)
  public void isValidAppTagException() throws IOException {
    when(APPS_LIST_MOCK.isValidAppTag(anyString(), anyString())).thenThrow(IOException.class);
    resources.client()
        .target("/configurations/test-platform/apps/test-app")
        .request()
        .get(String.class);
  }

  @Test(expected=NotFoundException.class)
  public void getConfigurationException() throws IOException {
    when(APPS_LIST_MOCK.isValidAppTag("test-platform", "test-app")).thenReturn(true);
    when(HTTP_SERVICE_MOCK.getConfiguration("test-platform", "test-app", "en-us", "en-us"))
        .thenThrow(IOException.class);

    resources.client()
        .target(
            "/configurations/test-platform/apps/test-app?ui-lang=en-us&content-lang=en-us")
        .request()
        .get(String.class);
  }

  @Test(expected=BadRequestException.class)
  public void getMobileApplicationConfigFails_invalidAppTag() throws IOException {
    when(APPS_LIST_MOCK.isValidAppTag("test-platform", "test-app")).thenReturn(false);

    resources.client()
        .target("/configurations/test-platform/apps/test-app")
        .request()
        .get(String.class);
  }

  @Test(expected=NotFoundException.class)
  public void getMobileApplicationConfigFails_notFound() throws IOException {
    when(APPS_LIST_MOCK.isValidAppTag("test-platform", "test-app")).thenReturn(true);
    when(HTTP_SERVICE_MOCK.getConfiguration("test-platform", "test-app", "en-us", "en-us"))
        .thenReturn(new EmptyMobileConfiguration());
    when(HTTP_SERVICE_MOCK.createSelfUrl("test-platform", "test-app"))
        .thenReturn(
            "/configurations/test-platform/apps/test-app?ui-lang=en-us&content-lang=en-us");

    resources.client()
        .target("/configurations/test-platform/apps/test-app?ui-lang=en-us&content-lang=en-us")
        .request()
        .get(String.class);
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
          "/configurations/test-platform/apps/test-app?ui-lang=en-us&content-lang=en-us");
    when(HTTP_SERVICE_MOCK.getConfiguration("test-platform", "test-app", "en-us", "en-us"))
        .thenReturn(cfgMock);

    String response = resources.client()
        .target(
            "/configurations/test-platform/apps/test-app?ui-lang=en-us&content-lang=en-us")
        .request()
        .get(String.class);

    assert(response).contains("modules");

    verify(APPS_LIST_MOCK).isValidAppTag("test-platform", "test-app");
    verify(HTTP_SERVICE_MOCK).getConfiguration("test-platform", "test-app", "en-us", "en-us");
    verify(HTTP_SERVICE_MOCK, never()).getDefault("test-platform");
  }
}
