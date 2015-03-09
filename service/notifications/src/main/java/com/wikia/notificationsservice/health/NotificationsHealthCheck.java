package com.wikia.notificationsservice.health;

import com.codahale.metrics.health.HealthCheck;

public class NotificationsHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
