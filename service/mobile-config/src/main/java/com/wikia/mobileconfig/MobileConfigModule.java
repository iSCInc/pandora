package com.wikia.mobileconfig;

import com.google.inject.AbstractModule;

import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;


public class MobileConfigModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
        .to(MobileConfigConfiguration.class);
    binder().install(new ConsulModule());

  }
}
