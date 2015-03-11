package com.wikia.mobileconfig;

import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.mobileconfig.exceptions.MobileConfigModuleNotSetException;
import com.wikia.mobileconfig.guicemodule.MobileConfigModuleBase;
import com.wikia.mobileconfig.guicemodule.MobileConfigModuleFactory;
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
  private MobileConfigModuleBase mobileConfigModule;

  public static void main(String[] args) throws Exception {
    new MobileConfigApplication().run(args);
  }

  @Override
  public String getName() {
    return "mobile-config";
  }

  @Override
  public void initialize(Bootstrap<MobileConfigConfiguration> bootstrap) {

    mobileConfigModule = MobileConfigModuleFactory.CreateMobileConfigModule();

    guiceBundle = GuiceBundle.<MobileConfigConfiguration>newBuilder()
        .addModule(mobileConfigModule)
        .addModule(new ConsulModule())
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
