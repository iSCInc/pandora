package com.wikia.mobileconfig;

import com.google.inject.Injector;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.mobileconfig.gateway.AppsDeployerListContainer;
import com.wikia.mobileconfig.health.AppsDeployerHealthCheck;
import com.wikia.mobileconfig.health.MobileConfigHealthCheck;
import com.wikia.mobileconfig.resources.ApplicationsResource;
import com.wikia.mobileconfig.resources.MobileConfigResource;
import com.wikia.mobileconfig.service.HttpConfigurationService;
import com.wikia.pandora.core.consul.ConsulBundle;
import com.wikia.pandora.core.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MobileConfigApplication extends Application<MobileConfigConfiguration> {

  public static final RepresentationFactory
      REPRESENTATION_FACTORY = new StandardRepresentationFactory();

  public static final Logger LOGGER = LoggerFactory.getLogger(MobileConfigApplication.class);

  public static void main(String[] args) throws Exception {
    new MobileConfigApplication().run(args);
  }

  @Override
  public String getName() {
    return "mobile-config";
  }

  @Override
  public void initialize(Bootstrap<MobileConfigConfiguration> bootstrap) {
    GuiceBundle<MobileConfigConfiguration> guiceBundle =
        GuiceBundle.<MobileConfigConfiguration>newBuilder()
            .addModule(new MobileConfigModule())
            .setInjectorFactory(new GovernatorInjectorFactory())
            .setConfigClass(MobileConfigConfiguration.class)
            .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();
    bootstrap.addBundle(
        injector.getInstance(ConsulVariableInterpolationBundle.class)
    );
    bootstrap.addBundle(
        injector.getInstance(ConsulBundle.class)
    );
  }


  @Override
  public void run(MobileConfigConfiguration configuration, Environment environment) {
    HttpConfigurationService configService = new HttpConfigurationService(
        environment,
        configuration
    );
    AppsDeployerListContainer
        listService =
        new AppsDeployerListContainer(environment, configuration);

    final MobileConfigHealthCheck healthCheck = new MobileConfigHealthCheck();
    environment.healthChecks().register("mobile-config", healthCheck);

    final AppsDeployerHealthCheck
        appsDeployerHealthCheck =
        new AppsDeployerHealthCheck(listService);
    environment.healthChecks().register("apps-deployer", appsDeployerHealthCheck);

    final MobileConfigResource
        mobileConfig =
        new MobileConfigResource(configService, listService);

    final ApplicationsResource
        appList =
        new ApplicationsResource(listService);

    environment.jersey().register(mobileConfig);
    environment.jersey().register(appList);
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
