package com.wikia.exampleservice;

import com.google.inject.Injector;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.exampleservice.configuration.ExampleServiceConfiguration;
import com.wikia.exampleservice.health.ExampleHealthCheck;
import com.wikia.exampleservice.resources.ExampleResource;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleServiceApplication extends Application<ExampleServiceConfiguration> {

  public static void main(String[] args) throws Exception {
    new ExampleServiceApplication().run(args);
  }

  @Override
  public String getName() {
    return "example"; // no uppercase letters
  }


  @Override
  public void initialize(Bootstrap<ExampleServiceConfiguration> bootstrap) {
    GuiceBundle<ExampleServiceConfiguration> guiceBundle =
        GuiceBundle.<ExampleServiceConfiguration>newBuilder()
            .addModule(new ExampleServiceModule())
            .setInjectorFactory(new GovernatorInjectorFactory())
            .setConfigClass(ExampleServiceConfiguration.class)
            .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();
    bootstrap.addBundle(
        injector.getInstance(ConsulVariableInterpolationBundle.class)
    );
    bootstrap.addBundle(
        injector.getInstance(ConsulBundle.class)
    );
  }

  @Override
  public void run(ExampleServiceConfiguration configuration, Environment environment)
      throws Exception {

    //register healthCheck (mandatory)
    final ExampleHealthCheck healthCheck = new ExampleHealthCheck();
    environment.healthChecks().register("SimpleHealthCheck", healthCheck);

    StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
    ExampleResource exampleResource = new ExampleResource(representationFactory);
    exampleResource.setGreetingsWord(configuration.getGreetingsWord());
    environment.jersey().register(exampleResource);

    //Optional
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
