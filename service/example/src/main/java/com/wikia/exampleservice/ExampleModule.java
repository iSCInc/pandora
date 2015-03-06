package com.wikia.exampleservice;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;

import com.wikia.exampleservice.configuration.ExampleConfiguration;
import com.wikia.pandora.core.consul.ConsulConfig;
import com.wikia.pandora.core.consul.ConsulGuiceModule;

public class ExampleModule extends AbstractModule {

  @Override
  protected void configure() {
    binder().install(new ConsulGuiceModule());
  }


  @Provides
  public ConsulConfig providesConsulConfig(Provider<ExampleConfiguration> configuration) {
    return configuration.get().getConsulConfig();
  }
}
