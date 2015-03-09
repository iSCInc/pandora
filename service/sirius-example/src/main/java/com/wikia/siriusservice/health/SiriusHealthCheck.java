package com.wikia.siriusservice.health;

import com.codahale.metrics.health.HealthCheck;

public class SiriusHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
