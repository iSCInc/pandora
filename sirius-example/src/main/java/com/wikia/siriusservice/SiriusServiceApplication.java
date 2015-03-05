package com.wikia.siriusservice;

import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.siriusservice.configuration.SiriusConfiguration;
import com.wikia.siriusservice.health.SiriusHealthCheck;
import com.wikia.siriusservice.resources.SiriusResource;
import com.wikia.pandora.core.jedis.JedisBundle;
import com.wikia.pandora.core.jedis.JedisFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SiriusServiceApplication extends Application<SiriusConfiguration> {

  private final String serviceName = "sirius-example";

  public static void main(String[] args) throws Exception {
    new SiriusServiceApplication().run(args);
  }

  @Override
  public String getName() {
    return this.serviceName;
  }


  @Override
  public void initialize(Bootstrap<SiriusConfiguration> bootstrap) {
    bootstrap.addBundle(new JedisBundle<SiriusConfiguration>() {
        @Override
        public JedisFactory getJedisFactory(SiriusConfiguration configuration) {
            return configuration.getJedisFactory();
        }
    });
  }

  @Override
  public void run(SiriusConfiguration configuration, Environment environment) throws Exception {

    //register healthCheck (mandatory)
    final SiriusHealthCheck healthCheck = new SiriusHealthCheck();
    environment.healthChecks().register("SimpleHealthCheck", healthCheck);

    StandardRepresentationFactory representationFactory = new StandardRepresentationFactory();
    SiriusResource siriusResource = new SiriusResource(representationFactory, configuration);
    environment.jersey().register(siriusResource);

    //Optional
    environment.jersey().register(JaxRsHalBuilderSupport.class);
  }
}
