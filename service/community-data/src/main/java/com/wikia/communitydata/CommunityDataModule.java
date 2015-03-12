package com.wikia.communitydata;

import com.wikia.communitydata.configuration.CommunityDataConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

public class CommunityDataModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class).to(CommunityDataConfiguration.class);
  }

  @Provides
  public RepresentationFactory getRepresentationFactory() {
    return new StandardRepresentationFactory();
  }
}
