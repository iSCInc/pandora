package com.wikia.userpreference;

import com.google.inject.AbstractModule;

import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.userpreference.configuration.UserPreferenceConfiguration;

public class UserPreferenceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
        .to(UserPreferenceConfiguration.class);
    binder().install(new ConsulModule());
  }

}
