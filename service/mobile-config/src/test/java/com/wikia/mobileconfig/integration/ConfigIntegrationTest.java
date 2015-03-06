package com.wikia.mobileconfig.integration;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.pandora.core.test.IntegrationTest;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import io.dropwizard.testing.junit.DropwizardAppRule;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.fest.assertions.api.Assertions.assertThat;

@Category(IntegrationTest.class)
public class ConfigIntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration>
      RULE = new DropwizardAppRule<>(
        MobileConfigApplication.class,
        resourceFilePath("mobile-config.yaml")
      );

  @Test
  public void unexistingConfigResourceRespondsWithError() {
    Client client = ClientBuilder.newClient();

    Response response = client.target(
        String.format("http://localhost:%d/configurations/test-platform/apps/test-app?ui-lang=en-us&content-lang=en-us", RULE.getLocalPort()))
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
  }

  @Test
  public void configResourceReturnsProperly() {
    Client client = ClientBuilder.newClient();

    Response response = client.target(
        String.format(
            "http://localhost:%d/configurations/android/apps/witcher?ui-lang=en-us&content-lang=en-us",
            RULE.getLocalPort()))
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
  }

  @Test
  public void configResourceReturnsProperlyWithoutTranslation() {
    Client client = ClientBuilder.newClient();

    Response response = client.target(
        String.format(
            "http://localhost:%d/configurations/android/apps/witcher?ui-lang=xx-xx&content-lang=xx-xx",
            RULE.getLocalPort()))
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
  }
}
