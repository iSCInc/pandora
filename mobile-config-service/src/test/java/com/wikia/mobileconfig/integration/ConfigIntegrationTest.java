package com.wikia.mobileconfig.integration;

import com.sun.jersey.api.Responses;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;

import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import io.dropwizard.testing.junit.DropwizardAppRule;

import static org.fest.assertions.api.Assertions.assertThat;

public class ConfigIntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration>
      RULE = new DropwizardAppRule<MobileConfigConfiguration>(
        MobileConfigApplication.class,
        "mobile-config.yml"
      );

  @Test
  public void unexistingConfigResourceRespondsWithError() {
    Client client = new Client();

    ClientResponse response = client.resource(
        String.format("http://localhost:%d/configurations/platform/test-platform/app/test-app?ui-lang=en-us&content-lang=en-us", RULE.getLocalPort()))
        .get(ClientResponse.class);

    assertThat(response.getStatus()).isEqualTo(Responses.CLIENT_ERROR);
  }

  @Test
  public void configResourceReturnsProperly() {
    Client client = new Client();

    ClientResponse response = client.resource(
        String.format("http://localhost:%d/configurations/platform/android/app/witcher?ui-lang=en-us&content-lang=en-us", RULE.getLocalPort()))
        .get(ClientResponse.class);

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
  }

  @Test
  public void configResourceReturnsProperlyWithoutTranslation() {
    Client client = new Client();

    ClientResponse response = client.resource(
        String.format("http://localhost:%d/configurations/platform/android/app/witcher?ui-lang=xx-xx&content-lang=xx-xx", RULE.getLocalPort()))
        .get(ClientResponse.class);

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
  }
}
