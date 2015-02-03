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

public class ImageIntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration>
      RULE = new DropwizardAppRule<MobileConfigConfiguration>(
        MobileConfigApplication.class,
        "mobile-config.yml"
      );

  @Test
  public void unexistingImageResourceRespondsWithError() {
    Client client = new Client();

    ClientResponse response = client.resource(
        String.format("http://localhost:%d/images/NonExistentImage.png", RULE.getLocalPort()))
        .get(ClientResponse.class);

    assertThat(response.getStatus()).isEqualTo(Responses.NOT_FOUND);
    assertThat(response.getType().getType()).isEqualTo("application");
    assertThat(response.getType().getSubtype()).isEqualTo("problem+json");
    assertThat(response.getEntity(String.class)).contains("NonExistentImage.png could not be found");
  }

  @Test
  public void imageResourceReturnedProperly() {
    Client client = new Client();

    ClientResponse response = client.resource(
        String.format("http://localhost:%d/images/test.png", RULE.getLocalPort()))
        .get(ClientResponse.class);

    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    assertThat(response.getType().getType()).isEqualTo("image");
    assertThat(response.getType().getSubtype()).isEqualTo("png");
    assertThat(response.getLength()).isGreaterThan(0);
  }
}
