package com.wikia.pandora.examples.health;

import com.codahale.metrics.health.HealthCheck;

public class PandoraHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}

