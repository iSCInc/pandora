package com.wikia.notificationsservice;

import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.notificationsservice.jedis.JedisBundle;
import com.wikia.notificationsservice.jedis.JedisFactory;
import com.wikia.notificationsservice.configuration.NotificationsConfiguration;
import com.wikia.notificationsservice.health.NotificationsHealthCheck;
import com.wikia.notificationsservice.resources.NotificationsResource;
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
