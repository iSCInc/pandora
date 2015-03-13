package com.wikia.communitydata;

import com.wikia.communitydata.configuration.CommunityDataConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import com.bendb.dropwizard.jooq.JooqBundle;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundle;

public class CommunityDataModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class).to(CommunityDataConfiguration.class);
  }

  @Provides
  public JooqBundle getJooqBundle() {
    return new JooqBundle<CommunityDataConfiguration>() {
      @Override
      public DataSourceFactory getDataSourceFactory(CommunityDataConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    };
  }

  @Provides
  public SwaggerBundle getSwaggerBundle() {
    return new SwaggerBundle<CommunityDataConfiguration>();
  }
}
