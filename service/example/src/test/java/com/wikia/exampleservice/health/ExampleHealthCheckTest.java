package com.wikia.exampleservice.health;

import static org.junit.Assert.assertTrue;

import com.codahale.metrics.health.HealthCheck;
import org.junit.Test;

public class ExampleHealthCheckTest {

  @Test
  public void testCheck() throws Exception {
    ExampleHealthCheck healthCheck = new ExampleHealthCheck();
    HealthCheck.Result result = healthCheck.check();
    assertTrue(result.isHealthy());
  }
}
