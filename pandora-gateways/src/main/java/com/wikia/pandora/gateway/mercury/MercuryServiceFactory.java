package com.wikia.pandora.gateway.mercury;


import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;
import com.wikia.pandora.core.impl.service.ServiceFactory;

import org.apache.commons.lang.NotImplementedException;
import org.apache.http.client.HttpClient;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

public class MercuryServiceFactory extends ServiceFactory {

  public MercuryServiceFactory(PandoraConfiguration configuration,
                               Environment environment) {
    super(configuration, environment);
  }

  @Override
  public ArticleService createArticleService() {
    final HttpClient httpClient = createHttpClient("gateway-client-articles");
    MercuryGateway gateway = new MercuryGateway(httpClient);
    return new MercuryArticlesService(gateway);
  }

  @Override
  public CommentService createCommentService() {
    throw new NotImplementedException();
  }

  private HttpClient createHttpClient(String name) {
    /// This HttpClient is deprecated should we use it? DropWizard using this.
    return new HttpClientBuilder(getEnvironment())
        .using(getConfiguration().getHttpClientConfiguration())
        .build(name);
  }
}
