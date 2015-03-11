package com.wikia.mobileconfig;

import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.mobileconfig.health.AppsDeployerHealthCheck;
import com.wikia.mobileconfig.health.MobileConfigHealthCheck;
import com.wikia.mobileconfig.resources.ApplicationsResource;
import com.wikia.mobileconfig.resources.MobileConfigResource;
import com.wikia.mobileconfig.service.application.AppsListService;
import com.wikia.mobileconfig.service.application.CephAppsListService;
import com.wikia.mobileconfig.service.configuration.CephConfigurationService;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;

import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileConfigApplication extends Application<MobileConfigConfiguration> {

  public static final RepresentationFactory
      REPRESENTATION_FACTORY = new StandardRepresentationFactory();

  public static final Logger LOGGER = LoggerFactory.getLogger(MobileConfigApplication.class);
  private GuiceBundle<MobileConfigConfiguration> guiceBundle;

  public static void main(String[] args) throws Exception {
    new MobileConfigApplication().run(args);
  }

  @Override
  public String getName() {
    return "mobile-config";
  }

  @Override
  public void initialize(Bootstrap<MobileConfigConfiguration> bootstrap) {
    guiceBundle = GuiceBundle.<MobileConfigConfiguration>newBuilder()
        .addModule(new MobileConfigModule())
        .setInjectorFactory(new GovernatorInjectorFactory())
        .enableAutoConfig(getClass().getPackage().getName())
        .setConfigClass(MobileConfigConfiguration.class)
        .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();
    bootstrap.addBundle(injector.getInstance(ConsulVariableInterpolationBundle.class));
    bootstrap.addBundle(injector.getInstance(ConsulBundle.class));
  }


  @Override
  public void run(MobileConfigConfiguration configuration, Environment environment) {
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
