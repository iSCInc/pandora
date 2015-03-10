package com.wikia.communitydata.configuration;

import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

public class CommunityDataConfiguration extends Configuration implements
                                                               ProvidesConsulConfiguration {
  private ConsulConfiguration consulConfiguration;

  public ConsulConfiguration getConsulConfiguration() {
    return consulConfiguration;
  }
}
