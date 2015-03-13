package com.wikia.communitydata;

import com.wikia.communitydata.configuration.CommunityDataConfiguration;
import com.wikia.dropwizard.consul.bundle.ConsulBundle;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.config.ConsulVariableInterpolationBundle;

import com.bendb.dropwizard.jooq.JooqBundle;
import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

public class CommunityDataApplication extends Application<CommunityDataConfiguration> {
  public static void main(String[] args) throws Exception {
    new CommunityDataApplication().run(args);
  }

  @Override
  public String getName() {
    return "community-data"; // no uppercase letters
  }


  @Override
  public void initialize(Bootstrap<CommunityDataConfiguration> bootstrap) {
    GuiceBundle<CommunityDataConfiguration> guiceBundle =
        GuiceBundle.<CommunityDataConfiguration>newBuilder()
            .addModule(new CommunityDataModule())
            .addModule(new ConsulModule())
            .enableAutoConfig(getClass().getPackage().getName())
            .setConfigClass(CommunityDataConfiguration.class)
            .build();

    bootstrap.addBundle(guiceBundle);

    Injector injector = guiceBundle.getInjector();

    bootstrap.addBundle(injector.getInstance(JooqBundle.class));
    bootstrap.addBundle(injector.getInstance(SwaggerBundle.class));
    bootstrap.addBundle(injector.getInstance(ConsulVariableInterpolationBundle.class));
    bootstrap.addBundle(injector.getInstance(ConsulBundle.class));
  }

  @Override
  public void run(CommunityDataConfiguration configuration, Environment environment)
      throws Exception {
    configureCors(environment);
  }

  private void configureCors(Environment environment) {
    FilterRegistration.Dynamic
        filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
    filter.setInitParameter("allowCredentials", "true");
  }
}
