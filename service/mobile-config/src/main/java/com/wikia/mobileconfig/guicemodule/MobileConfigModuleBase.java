package com.wikia.mobileconfig.guicemodule;

import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.mobileconfig.MobileConfigConfiguration;
import com.wikia.mobileconfig.service.configuration.CephConfigurationService;
import com.wikia.mobileconfig.service.configuration.ConfigurationService;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.netflix.governator.guice.lazy.LazySingleton;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;

import javax.inject.Named;

public abstract class MobileConfigModuleBase extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class).to(MobileConfigConfiguration.class);
    bind(ConfigurationService.class).to(CephConfigurationService.class);
  }

  @Provides
  @Named("apps-deployer-list-service")
  @LazySingleton
  public HttpClient provideAppsDeployerHtmlClient(MobileConfigConfiguration configuration,
                                                  Environment environment) {
    return new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build("apps-deployer-list-service");
  }

  @Provides
  @Named("http-configuration-service")
  @LazySingleton
  public HttpClient provideTestHtmlClient(MobileConfigConfiguration configuration,
                                          Environment environment) {
    return new HttpClientBuilder(environment)
        .using(configuration.getHttpClientConfiguration())
        .build("http-configuration-service");
  }
}
