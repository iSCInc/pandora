package com.wikia.communitydata.health;

import com.codahale.metrics.health.HealthCheck;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.wikia.communitydata.health.ExampleHealthCheck;

public class ExampleHealthCheckTest {

  @Test
  public void testCheck() throws Exception {
    ExampleHealthCheck healthCheck = new ExampleHealthCheck();
    HealthCheck.Result result = healthCheck.check();
    assertTrue(result.isHealthy());
  }
}
