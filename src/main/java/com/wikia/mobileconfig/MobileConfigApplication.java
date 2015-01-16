package com.wikia.mobileconfig;

import com.wikia.mobileconfig.service.FileConfigurationService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;

import com.wikia.mobileconfig.resources.MobileConfigResource;
import com.wikia.mobileconfig.health.MobileConfigHealthCheck;

public class MobileConfigApplication extends Application<MobileConfigConfiguration> {
    public static final RepresentationFactory representationFactory = new StandardRepresentationFactory();

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

        FileConfigurationService fileConfigService = new FileConfigurationService("fixtures");
        final MobileConfigResource mobileConfig = new MobileConfigResource(fileConfigService);
        environment.jersey().register(mobileConfig);
        environment.jersey().register(JaxRsHalBuilderSupport.class);
    }
}
