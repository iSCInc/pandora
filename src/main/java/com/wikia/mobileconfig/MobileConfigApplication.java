package com.wikia.mobileconfig;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.wikia.mobileconfig.resources.MobileConfigResource;
import com.wikia.mobileconfig.health.MobileConfigHealthCheck;

public class MobileConfigApplication extends Application<MobileConfigConfiguration> {
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
        final MobileConfigHealthCheck healthCheck = new MobileConfigHealthCheck();
        environment.healthChecks().register("mobile-config", healthCheck);

        final MobileConfigResource mobileConfig = new MobileConfigResource();
        environment.jersey().register(mobileConfig);
    }
}
