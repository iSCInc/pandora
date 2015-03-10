package com.wikia.communitydata;

import com.google.inject.AbstractModule;

import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.communitydata.configuration.CommunityDataConfiguration;

public class CommunityDataModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
        .to(CommunityDataConfiguration.class);
    binder().install(new ConsulModule());
  }

}
