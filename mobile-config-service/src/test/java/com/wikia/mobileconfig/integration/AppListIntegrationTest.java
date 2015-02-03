package com.wikia.mobileconfig.integration;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;

import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import io.dropwizard.testing.junit.DropwizardAppRule;

import static org.fest.assertions.api.Assertions.assertThat;

public class AppListIntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration>
      RULE = new DropwizardAppRule<MobileConfigConfiguration>(
        MobileConfigApplication.class,
        "mobile-config.yml"
      );

  @Test
  public void appListResourceReturnsProperly() {
    Client client = new Client();

    ClientResponse response = client.resource(
        String.format("http://localhost:%d/appList/platform/android", RULE.getLocalPort()))
        .get(ClientResponse.class);

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
  }
}
