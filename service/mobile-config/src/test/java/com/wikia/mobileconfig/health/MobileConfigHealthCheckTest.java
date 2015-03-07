package com.wikia.mobileconfig.health;

import static org.fest.assertions.api.Assertions.assertThat;

import com.codahale.metrics.health.HealthCheck.Result;
import org.junit.Before;
import org.junit.Test;

public class MobileConfigHealthCheckTest {
  private MobileConfigHealthCheck healthCheck;

  @Before
  public void initialize() {
    healthCheck = new MobileConfigHealthCheck();
  }

  @Test
  public void testHealthy() throws Exception {
    Result result = healthCheck.check();
    assertThat(result.isHealthy());
  }
}
