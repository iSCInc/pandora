package com.wikia.userpreference.health;

import com.codahale.metrics.health.HealthCheck;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserPreferenceHealthCheckTest {

  @Test
  public void testCheck() throws Exception {
    UserPreferenceHealthCheck healthCheck = new UserPreferenceHealthCheck();
    HealthCheck.Result result = healthCheck.check();
    assertTrue(result.isHealthy());
  }
}
