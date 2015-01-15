package com.wikia.pandora;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.pandora.service.ArticleService;
import com.wikia.pandora.service.CommentService;
import com.wikia.pandora.service.factory.MercuryServiceFactory;
import com.wikia.pandora.health.PandoraHealthCheck;
import com.wikia.pandora.resources.ArticleResource;
import com.wikia.pandora.service.factory.ServiceFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PandoraApplication extends Application<PandoraConfiguration> {

  private static RepresentationFactory representationFactory;

  public static void main(String[] args) throws Exception {
    new PandoraApplication().run(args);
  }

  public static RepresentationFactory getRepresentationFactory() {
    return representationFactory;
  }

  @Override
  public String getName() {
    return "Pandora";
  }

  @Override
  public void initialize(Bootstrap<PandoraConfiguration> bootstrap) {
  }

  @Override
  public void run(PandoraConfiguration configuration, Environment environment) {

    representationFactory = new StandardRepresentationFactory();

    final PandoraHealthCheck healthCheck = new PandoraHealthCheck();
    environment.healthChecks().register("pandora", healthCheck);

    ServiceFactory serviceFactory = new MercuryServiceFactory(configuration, environment);

    ArticleService articleService = serviceFactory.createArticleService();
    final ArticleResource articles = new ArticleResource(articleService);
    environment.jersey().register(articles);

    CommentService commentService = serviceFactory.createCommentService();

    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
