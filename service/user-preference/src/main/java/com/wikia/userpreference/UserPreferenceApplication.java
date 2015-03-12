package com.wikia.userpreference;

import com.google.inject.Injector;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.userpreference.configuration.UserPreferenceConfiguration;
import com.wikia.userpreference.health.UserPreferenceHealthCheck;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;
import com.wikia.userpreference.resources.UserPreferenceResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;

public class UserPreferenceApplication extends Application<UserPreferenceConfiguration> {

  public static void main(String[] args) throws Exception {
    new UserPreferenceApplication().run(args);
  }

  @Override
  public String getName() {
    return "user-preference"; // no uppercase letters
  }


  @Override
  public void initialize(Bootstrap<UserPreferenceConfiguration> bootstrap) {
    GuiceBundle<UserPreferenceConfiguration> guiceBundle =
        GuiceBundle.<UserPreferenceConfiguration>newBuilder()
            .addModule(new UserPreferenceModule())
            .setInjectorFactory(new GovernatorInjectorFactory())
            .setConfigClass(UserPreferenceConfiguration.class)
            .enableAutoConfig(getClass().getPackage().getName())
            .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();
    //bootstrap.addBundle(injector.getInstance(ConsulVariableInterpolationBundle.class));
    //bootstrap.addBundle(injector.getInstance(ConsulBundle.class));
    bootstrap.addBundle(new SwaggerBundle<UserPreferenceConfiguration>());
  }

  @Override
  public void run(UserPreferenceConfiguration configuration, Environment environment)
      throws Exception {

    //register healthCheck (mandatory)
    final UserPreferenceHealthCheck healthCheck = new UserPreferenceHealthCheck();
    environment.healthChecks().register("AmIUpHealthCheck", healthCheck);
  }
}
