package com.wikia.communitydata;

import com.google.inject.Injector;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import com.wikia.communitydata.configuration.CommunityDataConfiguration;
import com.wikia.communitydata.health.CommunityDataHealthCheck;
import com.wikia.communitydata.resources.CommunityDataResource;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CommunityDataApplication extends Application<CommunityDataConfiguration> {

  public static void main(String[] args) throws Exception {
    new CommunityDataApplication().run(args);
  }

  @Override
  public String getName() {
    return "community-data"; // no uppercase letters
  }


  @Override
  public void initialize(Bootstrap<CommunityDataConfiguration> bootstrap) {
    GuiceBundle<CommunityDataConfiguration> guiceBundle =
        GuiceBundle.<CommunityDataConfiguration>newBuilder()
            .addModule(new CommunityDataModule())
            .setInjectorFactory(new GovernatorInjectorFactory())
            .setConfigClass(CommunityDataConfiguration.class)
            .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();

    bootstrap.addBundle(injector.getInstance(ConsulVariableInterpolationBundle.class));
    bootstrap.addBundle(injector.getInstance(ConsulBundle.class));
  }

  @Override
  public void run(CommunityDataConfiguration configuration, Environment environment)
      throws Exception {

    //register healthCheck (mandatory)
    final CommunityDataHealthCheck healthCheck = new CommunityDataHealthCheck();
    environment.healthChecks().register("SimpleHealthCheck", healthCheck);

    StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
    CommunityDataResource communityDataResource = new CommunityDataResource(representationFactory, configuration.getWikicitiesDb());
    environment.jersey().register(communityDataResource);

    //Optional
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
