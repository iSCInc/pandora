package com.wikia.pandora.service.mercury;


import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;
import com.wikia.pandora.service.ServiceFactory;
import com.wikia.pandora.gateway.mercury.MercuryGateway;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.client.HttpClient;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

public class MercuryServiceFactory extends ServiceFactory {

  private final HttpClient httpClient;
  private final MercuryGateway gateway;

  public MercuryServiceFactory(PandoraConfiguration configuration,
                               Environment environment) {
    super(configuration, environment);
    this.httpClient = createHttpClient("gateway-client-articles");
    this.gateway = new MercuryGateway(httpClient, configuration.getMercuryGatewayHost(), configuration.getMercuryGatewayPort());
  }

  @Override
  public ArticleService createArticleService() {
    return new MercuryArticlesService(this.gateway);
  }

  @Override
  public CategoryService createCategoryService() {
    return new MercuryCategoryService(this.gateway);
  }

  @Override
  public CommentService createCommentService() {
    throw new NotImplementedException("");
  }

  private HttpClient createHttpClient(String name) {
    /// This HttpClient is deprecated should we use it? DropWizard using this.
    return new HttpClientBuilder(getEnvironment())
        .using(getConfiguration().getHttpClientConfiguration())
        .build(name);
  }
}
