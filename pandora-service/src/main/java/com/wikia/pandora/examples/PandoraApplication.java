package com.wikia.pandora.examples;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;
import com.wikia.pandora.core.impl.service.ServiceFactory;
import com.wikia.pandora.examples.health.PandoraHealthCheck;
import com.wikia.pandora.gateway.mediawiki.MediawikiServiceFactory;
import com.wikia.pandora.resources.HALCategoryResource;
import com.wikia.pandora.resources.HalArticleResource;
import com.wikia.pandora.resources.HalMercuryResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PandoraApplication extends Application<PandoraConfiguration> {

  private HalMercuryResource mercuryResource;

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
    RepresentationFactory representationFactory = new StandardRepresentationFactory();

    mercuryResource = new HalMercuryResource(articleService, representationFactory);
    environment.jersey().register(mercuryResource);

    environment.jersey().register(new HalArticleResource(articleService, representationFactory));
    environment.jersey().register(new HALCategoryResource(categoryService, representationFactory));

    environment.jersey().register(JaxRsHalBuilderSupport.class);
//    resourceFactory.registerResourcesInJersey(environment.jersey());
  }
}
