package com.wikia.mobileconfig;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import com.wikia.pandora.core.consul.ConsulConfig;
import com.wikia.pandora.core.consul.ConsulGuiceModule;


public class MobileConfigModule extends AbstractModule {

  public static final String APP_NAME = "mobile-config";

  private String environmentName;

  public MobileConfigModule() {
  }

  public MobileConfigModule(String environmentName) {
    this.environmentName = environmentName;
  }

  @Override
  protected void configure() {
    binder().install(new ConsulGuiceModule(APP_NAME, environmentName));
  }

  @Provides
  public ConsulConfig providesConsulConfig(Provider<MobileConfigConfiguration> configuration) {
    return configuration.get().getConsulConfig();
  }


}
