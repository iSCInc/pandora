package com.wikia.pandora.examples;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.api.service.RevisionService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;
import com.wikia.pandora.examples.health.PandoraHealthCheck;
import com.wikia.pandora.resources.HalArticleResource;
import com.wikia.pandora.resources.HALCategoryResource;
import com.wikia.pandora.resources.HalCommentResource;
import com.wikia.pandora.resources.HalRevisionResource;
import com.wikia.pandora.service.ServiceFactory;
import com.wikia.pandora.service.mediawiki.MediawikiServiceFactory;
import com.wikia.pandora.service.mercury.MercuryServiceFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PandoraApplication extends Application<PandoraConfiguration> {

  public static void main(String[] args) throws Exception {
    new PandoraApplication().run(args);
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

    final PandoraHealthCheck healthCheck = new PandoraHealthCheck();
    environment.healthChecks().register("pandora", healthCheck);

//    ServiceFactory serviceFactory = new MercuryServiceFactory(configuration, environment);
    ServiceFactory serviceFactory = new MediawikiServiceFactory(configuration, environment);

    ArticleService articleService = serviceFactory.createArticleService();
    CategoryService categoryService = serviceFactory.createCategoryService();
    CommentService commentService = serviceFactory.createCommentService();
    RevisionService revisionService = serviceFactory.createRevisionService();
    RepresentationFactory representationFactory = new StandardRepresentationFactory();

    environment.jersey().register(new HalArticleResource(articleService, representationFactory));
    environment.jersey().register(new HALCategoryResource(categoryService, representationFactory));
    environment.jersey().register(new HalCommentResource(commentService, representationFactory));
    environment.jersey().register(new HalRevisionResource(revisionService, representationFactory));

    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
