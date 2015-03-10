package com.wikia.communitydata.health;

import com.codahale.metrics.health.HealthCheck;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommunityDataHealthCheckTest {

  @Test
  public void testCheck() throws Exception {
    CommunityDataHealthCheck healthCheck = new CommunityDataHealthCheck();
    HealthCheck.Result result = healthCheck.check();
    assertTrue(result.isHealthy());
  }
}
