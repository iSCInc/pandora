package com.wikia.exampleservice.health;

import com.codahale.metrics.health.HealthCheck;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ExampleHealthCheckTest {
  private ExampleHealthCheck healthCheck;

  @Before
  public void initialize() {
    healthCheck = new ExampleHealthCheck();
  }

  @Test
  public void testHealthy() throws Exception {
    HealthCheck.Result result = healthCheck.check();
    assertTrue(result.isHealthy());
  }
}
