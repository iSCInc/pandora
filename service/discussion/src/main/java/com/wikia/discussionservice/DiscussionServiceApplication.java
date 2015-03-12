package com.wikia.discussionservice;

import com.google.inject.Injector;

import com.wikia.discussionservice.configuration.DiscussionServiceConfiguration;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

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

    // View bundle for the document views
    bootstrap.addBundle(new ViewBundle());

    // AssetBundle for the Hal Browser
    bootstrap.addBundle(new AssetsBundle());

    Injector injector = guiceBundle.getInjector();
    bootstrap.addBundle(
        injector.getInstance(ConsulVariableInterpolationBundle.class)
    );
    bootstrap.addBundle(
        injector.getInstance(ConsulBundle.class)
    );
  }

  @Override
  public void run(DiscussionServiceConfiguration configuration, Environment environment)
      throws Exception {
    // Register the JaxRsHalBuilderSupport to generate HAL Representation to
    // be compatible with HAL Browser and other industry standards
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
