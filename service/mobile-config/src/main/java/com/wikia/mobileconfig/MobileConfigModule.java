package com.wikia.mobileconfig;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;

import com.wikia.pandora.core.consul.ConsulConfig;
import com.wikia.pandora.core.consul.ConsulGuiceModule;


public class MobileConfigModule extends AbstractModule {
  @Override
  protected void configure() {
    binder().install(new ConsulGuiceModule());
  }

  @Provides
  public ConsulConfig providesConsulConfig(Provider<MobileConfigConfiguration> configuration) {
    return configuration.get().getConsulConfig();
  }
}
