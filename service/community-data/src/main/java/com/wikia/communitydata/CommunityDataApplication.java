package com.wikia.communitydata;

import com.bendb.dropwizard.jooq.JooqBundle;
import com.google.inject.Injector;

import com.hubspot.dropwizard.guice.GuiceBundle;

import com.wikia.communitydata.configuration.CommunityDataConfiguration;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
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
            .addModule(new ConsulModule())
            .enableAutoConfig(getClass().getPackage().getName())
            .setConfigClass(CommunityDataConfiguration.class)
            .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();

    bootstrap.addBundle(new JooqBundle<CommunityDataConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(CommunityDataConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });
    bootstrap.addBundle(injector.getInstance(ConsulVariableInterpolationBundle.class));
    bootstrap.addBundle(injector.getInstance(ConsulBundle.class));
  }

  @Override
  public void run(CommunityDataConfiguration configuration, Environment environment)
      throws Exception {

  }
}
