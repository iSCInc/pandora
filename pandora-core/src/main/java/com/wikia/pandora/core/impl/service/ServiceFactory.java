package com.wikia.pandora.core.impl.service;

import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public abstract class ServiceFactory {

  private final PandoraConfiguration configuration;
  private final Environment environment;

  public ServiceFactory(PandoraConfiguration configuration, Environment environment) {

    this.configuration = configuration;
    this.environment = environment;
  }


  public abstract ArticleService createArticleService();

  public abstract CommentService createCommentService();


  public PandoraConfiguration getConfiguration() {
    return configuration;
  }


  public Environment getEnvironment() {
    return environment;
  }


}
