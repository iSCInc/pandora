package com.wikia.discussionservice;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.wikia.discussionservice.configuration.DiscussionServiceConfiguration;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    // AssetBundle for the Hal Browser
    bootstrap.addBundle(new AssetsBundle());
  }

  @Override
  public void run(DiscussionServiceConfiguration configuration, Environment environment)
      throws Exception {
  }
}
