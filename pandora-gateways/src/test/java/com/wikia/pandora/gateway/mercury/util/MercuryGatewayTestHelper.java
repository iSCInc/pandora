package com.wikia.pandora.gateway.mercury.util;

import com.google.common.base.Optional;

import com.wikia.pandora.gateway.mercury.MercuryGateway;

import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.IOException;

import io.dropwizard.testing.FixtureHelpers;

public class MercuryGatewayTestHelper {


  public static MercuryGateway mercuryGatewayMock(String wikia, String title, String fixturePath)
      throws IOException {
    MercuryGateway mercuryGateway = Mockito.mock(MercuryGateway.class);
    Mockito.when(mercuryGateway.formatArticleMercuryRequest(wikia, title)).thenCallRealMethod();
    Mockito.when(mercuryGateway.formatMercuryRequest(wikia, title,
                                                     MercuryGateway.DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT))
        .thenCallRealMethod();
    Mockito.when(mercuryGateway.getArticle(wikia, title)).thenCallRealMethod();
    Mockito.when(mercuryGateway.validateMercuryArticleMap(Matchers.anyMap())).thenCallRealMethod();

    String requestUrl = mercuryGateway.formatArticleMercuryRequest(wikia, title);
    String json = FixtureHelpers.fixture(fixturePath);
    Mockito.when(mercuryGateway.getRequestHandler(requestUrl))
        .thenReturn(Optional.of(json));

    return mercuryGateway;
  }
}
