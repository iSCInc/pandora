package com.wikia.mobileconfig.health;

import com.codahale.metrics.health.HealthCheck;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;
import org.apache.commons.lang3.NotImplementedException;

public class MobileConfigHealthCheck extends InjectableHealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }

  @Override
  public String getName() {
    return "mobile-config";
  }
}
