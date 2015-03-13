package com.wikia.exampleservice;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.exampleservice.guicemodule.ExampleModuleFactory;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleServiceApplication extends Application<ExampleServiceConfiguration> {

  public static final RepresentationFactory
      REPRESENTATION_FACTORY = new StandardRepresentationFactory();

  public static final Logger LOGGER = LoggerFactory.getLogger(ExampleServiceApplication.class);

  private GuiceBundle<ExampleServiceConfiguration> guiceBundle;
  private AbstractModule exampleModule;

  public static void main(String[] args) throws Exception {
    new ExampleServiceApplication().run(args);
  }

  @Override
  public String getName() {
    return "example"; // no uppercase letters
  }


  @Override
  public void initialize(Bootstrap<ExampleServiceConfiguration> bootstrap) {

    exampleModule = ExampleModuleFactory.createExampleModule();

    guiceBundle = GuiceBundle.<ExampleServiceConfiguration>newBuilder()
        .addModule(exampleModule)
        .addModule(new ConsulModule())
        .setInjectorFactory(new GovernatorInjectorFactory())
        .enableAutoConfig(getClass().getPackage().getName())
        .setConfigClass(ExampleServiceConfiguration.class)
        .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();
    bootstrap.addBundle(injector.getInstance(ConsulVariableInterpolationBundle.class));
    bootstrap.addBundle(injector.getInstance(ConsulBundle.class));
  }

  @Override
  public void run(ExampleServiceConfiguration configuration, Environment environment)
      throws Exception {

    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
