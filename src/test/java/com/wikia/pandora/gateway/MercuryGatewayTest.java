package com.wikia.pandora.gateway;

import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static io.dropwizard.testing.FixtureHelpers.*;

import com.google.common.base.Optional;
import junit.framework.TestCase;
import org.apache.http.client.HttpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MercuryGatewayTest extends TestCase {

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

        MercuryGateway mercuryGateway = mock(MercuryGateway.class);
        when(mercuryGateway.formatMercuryRequest(wikia, title)).thenCallRealMethod();
        when(mercuryGateway.formatMercuryRequest(wikia, title, MercuryGateway.DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT)).thenCallRealMethod();
        when(mercuryGateway.getArticle(wikia, title)).thenCallRealMethod();

        String requestUrl = mercuryGateway.formatMercuryRequest(wikia, title);
        String json = fixture("fixtures/mercury-gateway/kermit-the-frog.json");
        when(mercuryGateway.getRequestHandler(requestUrl))
                .thenReturn(Optional.of(json));

        Map<String, Object> article = mercuryGateway.getArticle(wikia, title);
        System.out.println(article);
        assertThat(article.get("data")).isNotNull();
        assertThat(((Map<String,Object>)article.get("data")).get("details")).isNotNull();

    }

}