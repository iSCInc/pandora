package com.wikia.notificationsservice;

import com.bendb.dropwizard.redis.JedisBundle;
import com.bendb.dropwizard.redis.JedisFactory;
import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;
import com.wikia.notificationsservice.configuration.NotificationsConfiguration;
import com.wikia.notificationsservice.health.NotificationsHealthCheck;
import com.wikia.notificationsservice.resources.NotificationsResource;
import com.wikia.pandora.core.dropwizard.GovernatorInjectorFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class NotificationsServiceApplication extends Application<NotificationsConfiguration> {

  private final String serviceName = "notifications";

  public static void main(String[] args) throws Exception {
    new NotificationsServiceApplication().run(args);
  }

  @Override
  public String getName() {
    return this.serviceName;
  }


  @Override
  public void initialize(Bootstrap<NotificationsConfiguration> bootstrap) {
    GuiceBundle<NotificationsConfiguration> guiceBundle =
            GuiceBundle.<NotificationsConfiguration>newBuilder()
                    .addModule(new NotificationsModule())
                    .setInjectorFactory(new GovernatorInjectorFactory())
                    .setConfigClass(NotificationsConfiguration.class)
                    .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();
    bootstrap.addBundle(
            injector.getInstance(ConsulVariableInterpolationBundle.class)
    );
    bootstrap.addBundle(
            injector.getInstance(ConsulBundle.class)
    );

    bootstrap.addBundle(new JedisBundle<NotificationsConfiguration>() {
      @Override
      public JedisFactory getJedisFactory(NotificationsConfiguration configuration) {
        return configuration.getJedisFactory();
      }
    });
  }

  @Override
  public void run(NotificationsConfiguration configuration, Environment environment) throws Exception {

    //register healthCheck (mandatory)
    final NotificationsHealthCheck healthCheck = new NotificationsHealthCheck();
    environment.healthChecks().register("SimpleHealthCheck", healthCheck);

    StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
    NotificationsResource notificationsResource = new NotificationsResource(representationFactory, configuration);
    environment.jersey().register(notificationsResource);

    //Optional
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
