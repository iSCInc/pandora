package com.wikia.mobileconfig.health;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.wikia.mobileconfig.gateway.AppsListService;

import com.codahale.metrics.health.HealthCheck.Result;
import org.junit.Before;
import org.junit.Test;

public class AppsDeployerHealthCheckTest {
  private AppsListService service;
  private AppsDeployerHealthCheck healthCheck;

  @Before
  public void initialize() {
    service = mock(AppsListService.class);
    healthCheck = new AppsDeployerHealthCheck(service);
  }

  @Test
  public void testHealthy() throws Exception {
    when(service.isUp()).thenReturn(true);
    Result result = healthCheck.check();
    assertThat(result.isHealthy());
  }

  @Test
  public void testUnhealthy() throws Exception {
    when(service.isUp()).thenReturn(false);
    Result result = healthCheck.check();
    assertThat(!result.isHealthy());
  }
}
