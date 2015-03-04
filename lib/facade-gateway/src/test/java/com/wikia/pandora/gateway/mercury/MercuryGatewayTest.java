package com.wikia.pandora.gateway.mercury;


import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.dropwizard.testing.FixtureHelpers;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class MercuryGatewayTest {

  @Test
  public void testFormatMercuryRequest() throws URISyntaxException {
    HttpClient httpClient = mock(HttpClient.class);

    MercuryGateway mercuryGateway = new MercuryGateway(httpClient);
    assertThat(mercuryGateway.mercuryArticleRequestURI("muppet", "Kermit the Frog").toString())
        .isEqualTo("http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit+the+Frog");
  }

  @Test
  public void testGetArticle() throws IOException {
    String wikia = "muppet";
    String title = "Kermit the Frog";

    HttpClient httpClient = mock(HttpClient.class);
    MercuryGateway mercuryGateway = new MercuryGateway(httpClient);

    when(httpClient.execute(any(HttpGet.class), any(ResponseHandler.class))).thenReturn(
        FixtureHelpers.fixture("fixtures/mercury-gateway/kermit-the-frog.json")
    );

    Map<String,Object> article = mercuryGateway.getArticle(wikia, title);
    assertThat(article.get("data")).isNotNull();

    Map<String,Object> data = (Map<String, Object>) article.get("data");
    assertThat(data.get("details")).isNotNull();

    Map<String,Object> details = (Map<String, Object>) data.get("details");
    assertThat(details.get("title")).isEqualTo("Kermit the Frog");

  }
}
