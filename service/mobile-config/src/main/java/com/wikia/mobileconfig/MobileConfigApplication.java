package com.wikia.mobileconfig;

import com.google.inject.Module;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.mobileconfig.gateway.AppsDeployerListContainer;
import com.wikia.mobileconfig.health.AppsDeployerHealthCheck;
import com.wikia.mobileconfig.health.MobileConfigHealthCheck;
import com.wikia.mobileconfig.resources.ApplicationsResource;
import com.wikia.mobileconfig.resources.ImageResource;
import com.wikia.mobileconfig.resources.MobileConfigResource;
import com.wikia.mobileconfig.service.HttpConfigurationService;
import com.wikia.mobileconfig.service.ImageService;
import com.wikia.pandora.core.consul.ConsulBundle;
import com.wikia.pandora.core.consul.config.ConsulVariableInterpolationBundle;

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

  public Module getModule() {
    return new MobileConfigModule();
  }

  @Override
  public String getName() {
    return MobileConfigModule.APP_NAME;
  }

  @Override
  public void initialize(Bootstrap<MobileConfigConfiguration> bootstrap) {
    GuiceBundle<MobileConfigConfiguration> guiceBundle =
        GuiceBundle.<MobileConfigConfiguration>newBuilder()
            .addModule(getModule())
            .setConfigClass(MobileConfigConfiguration.class)
            .enableAutoConfig(
                ConsulVariableInterpolationBundle.class.getPackage().getName(),
                ConsulBundle.class.getPackage().getName()
            ).build();

    bootstrap.addBundle(guiceBundle);
  }

  @Override
  public void run(MobileConfigConfiguration configuration, Environment environment) {
    HttpConfigurationService configService = new HttpConfigurationService(
        environment,
        configuration
    );
    ImageService imageService = new ImageService(
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

    final ImageResource
        image =
        new ImageResource(imageService);

    final ApplicationsResource
        appList =
        new ApplicationsResource(listService);

    environment.jersey().register(mobileConfig);
    environment.jersey().register(image);
    environment.jersey().register(appList);
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
