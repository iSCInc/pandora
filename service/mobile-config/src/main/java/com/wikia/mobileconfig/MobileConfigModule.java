package com.wikia.mobileconfig;

import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.mobileconfig.service.application.AppsDeployerListContainer;
import com.wikia.mobileconfig.service.application.AppsListService;
import com.wikia.mobileconfig.service.application.CephAppsListService;
import com.wikia.mobileconfig.service.configuration.CephConfigurationService;
import com.wikia.mobileconfig.service.configuration.ConfigurationService;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.netflix.governator.guice.lazy.LazySingleton;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MobileConfigModule extends AbstractModule {

  private static Logger LOGGER = LoggerFactory.getLogger(MobileConfigModule.class);

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class).to(MobileConfigConfiguration.class);
    binder().install(new ConsulModule());

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

  @Provides
  public AppsListService provideAppsListServiceFromConfiguration(
      Injector injector,
      MobileConfigConfiguration configuration) {
    AppsListService appsListService = null;

    ConsulConfiguration consulConfiguration = configuration.getConsulConfiguration();
    if (consulConfiguration.getTags().contains("qa")) {
      appsListService = injector.getInstance(CephAppsListService.class);
      LOGGER.info("qa tag found, using CephAppsListService");
    } else {
      appsListService = injector.getInstance(AppsDeployerListContainer.class);
    }
    return appsListService;
  }
}
