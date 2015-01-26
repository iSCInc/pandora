package com.wikia.pandora.gateway.mediawiki;

import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;
import com.wikia.pandora.core.impl.service.ServiceFactory;

import org.apache.commons.lang.NotImplementedException;
import org.apache.http.client.HttpClient;

import io.dropwizard.setup.Environment;

import static com.wikia.pandora.core.util.HttpClientBuilderHelper.createHttpClient;

public class MediawikiServiceFactory extends ServiceFactory {

  private final HttpClient httpClient;
  private final MediawikiGateway gateway;

  public MediawikiServiceFactory(PandoraConfiguration configuration,
                                 Environment environment) {
    super(configuration, environment);
    httpClient =
        createHttpClient(this.getEnvironment(), this.getConfiguration(), "mediawiki-gateway");
    gateway = new MediawikiGateway(httpClient);
  }

  @Override
  public ArticleService createArticleService() {

    return new MediawikiArticleService(gateway);
  }

  @Override
  public CategoryService createCategoryService() {
    return new MediawikiCategoryService(gateway);
  }

  @Override
  public CommentService createCommentService() {

    throw new NotImplementedException();
  }
}
