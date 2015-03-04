package com.wikia.exampleservice;

import com.wikia.exampleservice.configuration.ExampleConfiguration;
import com.wikia.exampleservice.health.ExampleHealthCheck;
import com.wikia.exampleservice.resources.ExampleResource;
import com.wikia.pandora.core.consul.ConsulBundle;
import com.wikia.pandora.core.consul.ConsulConfig;

import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleServiceApplication extends Application<ExampleConfiguration> {

  public static void main(String[] args) throws Exception {
    new ExampleServiceApplication().run(args);
  }

  @Override
  public String getName() {
    return "pandora-example"; // no uppercase letters
  }


  @Override
  public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    bootstrap.addBundle(new ConsulBundle<ExampleConfiguration>() {
      @Override
      protected ConsulConfig narrowConfig(ExampleConfiguration config) {
        return config.getConsulConfig();
      }
    });
  }

  @Override
  public void run(ExampleConfiguration configuration, Environment environment) throws Exception {

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
