package com.wikia.mobileconfig.health;

import com.codahale.metrics.health.HealthCheck;

import com.wikia.mobileconfig.service.AppsListService;

public class AppsDeployerHealthCheck extends HealthCheck {
    private final AppsListService service;

    public AppsDeployerHealthCheck(AppsListService service) {
        this.service = service;
    }

    @Override
    protected Result check() throws Exception {
        if( this.service.isUp() ) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Cannot connect to apps-deployer-panel");
        }
    }
}
