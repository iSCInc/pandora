package com.wikia.pandora.gateway;

import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.assertThat;

import com.wikia.pandora.gateway.util.MercuryGatewayTestHelper;
import org.apache.http.client.HttpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class MercuryGatewayTest {

    @Test
    public void testFormatMercuryRequest() {
        HttpClient httpClient = mock(HttpClient.class);

        MercuryGateway mercuryGateway = new MercuryGateway(httpClient);
        assertThat(mercuryGateway.formatMercuryRequest("muppet", "Kermit the Frog"))
                .isEqualTo("http://muppet.wikia.com/api/v1/Mercury/Article?title=Kermit+the+Frog");
    }

    @Test
    public void testGetArticle() throws IOException {
        String wikia = "muppet";
        String title = "Kermit the Frog";

        MercuryGateway mercuryGateway = MercuryGatewayTestHelper.mercuryGatewayMock(wikia, title,
                "fixtures/mercury-gateway/kermit-the-frog.json");
        Map<String, Object> article = mercuryGateway.getArticle(wikia, title);

        assertThat(article.get("data")).isNotNull();
        assertThat(((Map<String,Object>)article.get("data")).get("details")).isNotNull();
    }


}