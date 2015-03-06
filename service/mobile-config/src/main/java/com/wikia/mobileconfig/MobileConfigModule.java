package com.wikia.mobileconfig;

import com.google.inject.AbstractModule;

import com.wikia.pandora.core.consul.ConsulModule;
import com.wikia.pandora.core.consul.ProvidesConsulConfiguration;


public class MobileConfigModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
        .to(MobileConfigConfiguration.class);
    binder().install(new ConsulModule());

  }
}
