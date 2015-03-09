package com.wikia.mobileconfig.health;


import com.wikia.mobileconfig.gateway.AppsListService;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;

public class AppsDeployerHealthCheck extends HealthCheck {

  private final static String CONNECTION_ERROR_MSG = "Cannot connect to apps-deployer-panel";
  private final AppsListService service;

  @Inject
  public AppsDeployerHealthCheck(AppsListService service) {
    this.service = service;
  }

  @Override
  protected Result check() throws Exception {
    if (this.service.isUp()) {
      return Result.healthy();
    } else {
      return Result.unhealthy(CONNECTION_ERROR_MSG);
    }
  }
}
