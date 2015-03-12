package com.wikia.userpreference.health;

import com.codahale.metrics.health.HealthCheck;

public class UserPreferenceHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
