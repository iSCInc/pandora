package com.wikia.discussionservice;

import com.wikia.discussionservice.configuration.DiscussionServiceConfiguration;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;

public class DiscussionServiceApplication extends Application<DiscussionServiceConfiguration> {

  public static void main(String[] args) throws Exception {
    new DiscussionServiceApplication().run(args);
  }

  @Override
  public String getName() {
    return "discussion-service"; // no uppercase letters
  }

  @Override
  public void initialize(Bootstrap<DiscussionServiceConfiguration> bootstrap) {
    GuiceBundle<DiscussionServiceConfiguration> guiceBundle =
        GuiceBundle.<DiscussionServiceConfiguration>newBuilder()
            .addModule(new DiscussionServiceModule())
            .setConfigClass(DiscussionServiceConfiguration.class)
            .enableAutoConfig(getClass().getPackage().getName())
            .build();
    bootstrap.addBundle(guiceBundle);

    // Swagger bundle for the SwaggerUI
    bootstrap.addBundle(new SwaggerBundle<DiscussionServiceConfiguration>());
  }

  @Override
  public void run(DiscussionServiceConfiguration configuration, Environment environment)
      throws Exception {
    // Register the JaxRsHalBuilderSupport to generate HAL Representation to
    // be compatible with HAL Browser and other industry standards
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }

}
