package com.wikia.pandora;

import com.wikia.pandora.resources.ResourceFactory;
import com.wikia.pandora.resources.halbuilder.HalResourceFactory;
import com.wikia.pandora.service.mediawikiapi.MediawikiServiceFactory;
import com.wikia.pandora.service.mercury.MercuryServiceFactory;
import com.wikia.pandora.health.PandoraHealthCheck;
import com.wikia.pandora.service.ServiceFactory;

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
    ResourceFactory resourceFactory = new HalResourceFactory(serviceFactory);

    resourceFactory.registerResourcesInJersey(environment.jersey());
  }
}
