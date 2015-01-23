package com.wikia.pandora.gateway.mediawiki;

import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;
import com.wikia.pandora.core.impl.service.ServiceFactory;

import org.apache.commons.lang.NotImplementedException;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class MediawikiServiceFactory extends ServiceFactory {

  public MediawikiServiceFactory(PandoraConfiguration configuration,
                                 Environment environment) {
    super(configuration, environment);
  }

  @Override
  public ArticleService createArticleService() {
    return new MediawikiArticleService();
  }

  @Override
  public CommentService createCommentService() {

    throw new NotImplementedException();
  }
}
