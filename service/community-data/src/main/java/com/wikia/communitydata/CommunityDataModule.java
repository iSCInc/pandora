package com.wikia.communitydata;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import com.wikia.communitydata.configuration.MysqlConfiguration;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.communitydata.configuration.CommunityDataConfiguration;

public class CommunityDataModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class).to(CommunityDataConfiguration.class);
  }

  @Provides
  public MysqlConfiguration getMysqlConfiguration(CommunityDataConfiguration config) {
    return config.getWikicitiesDb();
  }

  @Provides
  public RepresentationFactory getRepresentationFactory() {
    return new StandardRepresentationFactory();
  }
}
