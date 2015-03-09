package com.wikia.mobileconfig.integration;

import com.squarespace.jersey2.guice.BootstrapUtils;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.pandora.core.test.IntegrationTest;

import org.junit.AfterClass;
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
public class ApplicationsIntegrationTest {

  @ClassRule
  public static final DropwizardAppRule<MobileConfigConfiguration> RULE = new DropwizardAppRule<>(
      MobileConfigApplication.class,
      resourceFilePath("mobile-config.yaml")
  );

  @AfterClass
  public static void tearDown() {
    BootstrapUtils.reset();
  }

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
