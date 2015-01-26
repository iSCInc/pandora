package com.wikia.pandora.gateway.mercury;


import com.wikia.pandora.gateway.mercury.util.MercuryGatewayTestHelper;

import org.apache.http.client.HttpClient;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class MercuryGatewayTest {

  @Test
  public void testFormatMercuryRequest() {
    HttpClient httpClient = Mockito.mock(HttpClient.class);

    MercuryGateway mercuryGateway = new MercuryGateway(httpClient);
    assertThat(mercuryGateway.formatArticleMercuryRequest("muppet", "Kermit the Frog"))
        .isEqualTo("http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit+the+Frog");
  }

  @Test
  public void testGetArticle() throws IOException {
    String wikia = "muppet";
    String title = "Kermit the Frog";

    MercuryGateway mercuryGateway = MercuryGatewayTestHelper.mercuryGatewayMock(wikia, title,
                                                                                "fixtures/mercury-gateway/kermit-the-frog.json");
    Map<String, Object> article = mercuryGateway.getArticle(wikia, title);

    Assertions.assertThat(article.get("data")).isNotNull();
    Assertions.assertThat(((Map<String, Object>) article.get("data")).get("details")).isNotNull();
  }
}
