package com.wikia.mobileconfig;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.mobileconfig.health.AppsDeployerHealthCheck;
import com.wikia.mobileconfig.health.MobileConfigHealthCheck;
import com.wikia.mobileconfig.resources.AppListResource;
import com.wikia.mobileconfig.resources.ImageResource;
import com.wikia.mobileconfig.resources.MobileConfigResource;
import com.wikia.mobileconfig.service.AppsDeployerList;
import com.wikia.mobileconfig.service.FileConfigurationService;
import com.wikia.mobileconfig.service.HttpConfigurationService;
import com.wikia.mobileconfig.service.ImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MobileConfigApplication extends Application<MobileConfigConfiguration> {

  public static final RepresentationFactory
      representationFactory =
      new StandardRepresentationFactory();
  public static final Logger logger = LoggerFactory.getLogger(FileConfigurationService.class);

  public static void main(String[] args) throws Exception {
    new MobileConfigApplication().run(args);
  }

  @Override
  public String getName() {
    return "mobile-config";
  }

  @Override
  public void initialize(Bootstrap<MobileConfigConfiguration> bootstrap) {
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
    AppsDeployerList listService = new AppsDeployerList(environment, configuration);

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

    final AppListResource
        appList =
        new AppListResource(listService);
    environment.jersey().register(mobileConfig);
    environment.jersey().register(image);
    environment.jersey().register(appList);
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
