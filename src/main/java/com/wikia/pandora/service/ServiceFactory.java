package com.wikia.pandora.service;

import com.wikia.pandora.PandoraConfiguration;
import com.wikia.pandora.service.ArticleService;
import com.wikia.pandora.service.CommentService;

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
