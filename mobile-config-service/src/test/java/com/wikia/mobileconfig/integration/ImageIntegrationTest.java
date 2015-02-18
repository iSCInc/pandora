package com.wikia.mobileconfig.integration;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.pandora.test.IntegrationTest;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import io.dropwizard.testing.junit.DropwizardAppRule;

import static org.fest.assertions.api.Assertions.assertThat;

@Category(IntegrationTest.class)
public class ImageIntegrationTest {
  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration>
      RULE = new DropwizardAppRule<MobileConfigConfiguration>(
        MobileConfigApplication.class,
        "mobile-config.yml"
      );

  @Test
  public void unexistingImageResourceRespondsWithError() {
    Client client = ClientBuilder.newClient();

    Response response = client.target(
        String.format("http://localhost:%d/images/NonExistentImage.png", RULE.getLocalPort()))
        .request()
        .get();

    String responseData = response.readEntity(String.class);
    assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    assertThat(response.getHeaderString("content-type")).isEqualTo("application/problem+json");
    assertThat(responseData).contains("NonExistentImage.png could not be found");
  }

  @Test
  public void imageResourceReturnedProperly() {
    Client client = ClientBuilder.newClient();

    Response response = client.target(
        String.format("http://localhost:%d/images/test.png", RULE.getLocalPort()))
        .request()
        .get();

    byte[] responseData = response.readEntity(byte[].class);
    assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    assertThat(response.getHeaderString("content-type")).isEqualTo("image/png");
    assertThat(responseData.length).isGreaterThan(0);
  }
}
