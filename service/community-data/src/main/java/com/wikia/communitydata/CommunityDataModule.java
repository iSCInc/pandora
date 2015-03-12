package com.wikia.communitydata;

import com.wikia.communitydata.configuration.CommunityDataConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import com.google.inject.AbstractModule;

public class CommunityDataModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class).to(CommunityDataConfiguration.class);
  }
}
