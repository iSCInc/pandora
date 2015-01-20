package com.wikia.pandora.service.mediawikiapi;

import com.wikia.pandora.PandoraConfiguration;
import com.wikia.pandora.service.ArticleService;
import com.wikia.pandora.service.CommentService;
import com.wikia.pandora.service.ServiceFactory;

import org.apache.commons.lang.NotImplementedException;

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
