package com.wikia.communitydata.health;

import com.codahale.metrics.health.HealthCheck;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class CommunityDataHealthCheck extends InjectableHealthCheck {
  @Override
  public String getName() {
    return "simple-check";
  }

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
