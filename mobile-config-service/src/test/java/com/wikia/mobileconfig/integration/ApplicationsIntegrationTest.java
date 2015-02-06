package com.wikia.mobileconfig.integration;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

import static org.fest.assertions.api.Assertions.assertThat;

public class ApplicationsIntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration>
      RULE = new DropwizardAppRule<MobileConfigConfiguration>(
        MobileConfigApplication.class,
        "mobile-config.yml"
      );

  @Test
  public void appListResourceReturnsProperly() {
    Client client = ClientBuilder.newClient();

    Response response = client.target(
        String.format("http://localhost:%d/applications/platform/android", RULE.getLocalPort()))
        .request()
        .get();

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
  }
}
