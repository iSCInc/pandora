package com.wikia.communitydata.configuration;

import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

public class CommunityDataConfiguration extends Configuration implements
                                                               ProvidesConsulConfiguration {
  private ConsulConfiguration consulConfiguration;

  @NotNull
  private DataSourceFactory dataSourceFactory;

  public ConsulConfiguration getConsulConfiguration() {
    return consulConfiguration;
  }

  public DataSourceFactory getDataSourceFactory() {
    return dataSourceFactory;
  }
}
