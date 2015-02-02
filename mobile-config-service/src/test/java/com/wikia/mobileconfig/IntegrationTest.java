package com.wikia.mobileconfig;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.junit.DropwizardAppRule;

import static org.fest.assertions.api.Assertions.assertThat;

public class IntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration>
      RULE = new DropwizardAppRule<MobileConfigConfiguration>(
        MobileConfigApplication.class,
        "mobile-config.yml"
      );

  @Test
  public void unexistingResourceRespondsWith404() {
    Client client = new Client();

    ClientResponse response = client.resource(
        String.format("http://localhost:%d/configurations/platform/test-platform/app/test-app/", RULE.getLocalPort()))
        .get(ClientResponse.class);

    assertThat(response.getStatus()).isEqualTo(404);
  }
}
