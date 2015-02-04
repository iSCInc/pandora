package com.wikia.pandora.gateway.mercury;




import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.fest.assertions.api.Assertions.*;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Map;

import io.dropwizard.testing.FixtureHelpers;

import static org.fest.assertions.api.Assertions.assertThat;

public class MercuryGatewayTest {

  @Test
  public void testFormatMercuryRequest() {
    HttpClient httpClient = mock(HttpClient.class);

    MercuryGateway mercuryGateway = new MercuryGateway(httpClient, "some-internal-host", 80);
    assertThat(mercuryGateway.formatArticleMercuryRequest("Kermit the Frog"))
        .isEqualTo("http://some-internal-host:80/api/v1/Mercury/Article?title=Kermit+the+Frog");
  }

  @Test
  public void testGetArticle() throws IOException {
    String wikia = "muppet";
    String title = "Kermit the Frog";

    HttpClient httpClient = mock(HttpClient.class);
    MercuryGateway mercuryGateway = new MercuryGateway(httpClient, "some-internal-host", 80);

    when(httpClient.execute(any(HttpGet.class), any(ResponseHandler.class))).thenReturn(
        FixtureHelpers.fixture("fixtures/mercury-gateway/kermit-the-frog.json").toString()
    );

    Map<String,Object> article = mercuryGateway.getArticle(wikia, title);
    assertThat(article.get("data")).isNotNull();

    Map<String,Object> data = (Map<String, Object>) article.get("data");
    assertThat(data.get("details")).isNotNull();

    Map<String,Object> details = (Map<String, Object>) data.get("details");
    assertThat(details.get("title")).isEqualTo("Kermit the Frog");

  }
}
