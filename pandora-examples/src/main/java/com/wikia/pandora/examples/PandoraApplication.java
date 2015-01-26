package com.wikia.pandora.examples;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.impl.configuration.PandoraConfiguration;
import com.wikia.pandora.core.impl.service.ServiceFactory;
import com.wikia.pandora.examples.health.PandoraHealthCheck;
import com.wikia.pandora.gateway.mercury.MercuryServiceFactory;
import com.wikia.pandora.resources.HalArticleResource;

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

    ServiceFactory serviceFactory = new MercuryServiceFactory(configuration, environment);
    ArticleService articleService = serviceFactory.createArticleService();
    RepresentationFactory representationFactory = new StandardRepresentationFactory();
    environment.jersey().register(new HalArticleResource(articleService, representationFactory));
    environment.jersey().register(JaxRsHalBuilderSupport.class);
//    resourceFactory.registerResourcesInJersey(environment.jersey());
  }
}
