package com.wikia.pandora.gateway.util;

import static org.mockito.Mockito.*;
import static io.dropwizard.testing.FixtureHelpers.*;

import com.google.common.base.Optional;

import com.wikia.pandora.service.mercury.MercuryGateway;

import java.io.IOException;

public class MercuryGatewayTestHelper {


  public static MercuryGateway mercuryGatewayMock(String wikia, String title, String fixturePath)
      throws IOException {
    MercuryGateway mercuryGateway = mock(MercuryGateway.class);
    when(mercuryGateway.formatArticleMercuryRequest(wikia, title)).thenCallRealMethod();
    when(mercuryGateway.formatMercuryRequest(wikia, title,
                                             MercuryGateway.DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT))
        .thenCallRealMethod();
    when(mercuryGateway.getArticle(wikia, title)).thenCallRealMethod();
    when(mercuryGateway.validateMercuryArticleMap(anyMap())).thenCallRealMethod();

    String requestUrl = mercuryGateway.formatArticleMercuryRequest(wikia, title);
    String json = fixture(fixturePath);
    when(mercuryGateway.getRequestHandler(requestUrl))
        .thenReturn(Optional.of(json));

    return mercuryGateway;
  }
}
