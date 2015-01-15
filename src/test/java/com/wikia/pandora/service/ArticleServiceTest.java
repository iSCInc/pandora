package com.wikia.pandora.service;

import static org.fest.assertions.api.Assertions.assertThat;

import com.google.common.base.Optional;

import com.wikia.pandora.core.Article;
import com.wikia.pandora.service.mercury.MercuryGateway;
import com.wikia.pandora.gateway.util.MercuryGatewayTestHelper;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

public class ArticleServiceTest {

  @Test
  public void testLookup() throws IOException {
//    String wikia = "muppet";
//    String title = "Kermit the Frog";
//
//    MercuryGateway mercuryGateway = MercuryGatewayTestHelper.mercuryGatewayMock(wikia, title,
//                                                                                "fixtures/mercury-gateway/kermit-the-frog.json");
//    ArticleService articleService = new ArticleService(mercuryGateway);
//    Optional<Article> article = articleService.lookup(wikia, title);
//    assertTrue(article.isPresent());
//    assertEquals((Integer) 50, article.get().id);
//    assertEquals("Kermit the Frog", article.get().title);
//    assertThat(article.get().content).containsIgnoringCase("jim_henson");
//  }
//
//  @Test(expected = java.lang.IllegalArgumentException.class)
//  public void testFailedLookup() throws IOException {
//    String wikia = "foo";
//    String title = "bogus";
//
//    MercuryGateway mercuryGateway = MercuryGatewayTestHelper.mercuryGatewayMock(wikia, title,
//                                                                                "fixtures/mercury-gateway/bogus.json");
//    ArticleService articleService = new ArticleService(mercuryGateway);
//    Optional<Article> article = articleService.lookup(wikia, title);
  }

}