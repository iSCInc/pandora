package com.wikia.communitydata.health;

import com.codahale.metrics.health.HealthCheck;

public class CommunityDataHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
