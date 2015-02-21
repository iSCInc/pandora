package com.wikia.discussionservice.health;

import com.codahale.metrics.health.HealthCheck;

import org.springframework.stereotype.Service;

@Service
public class DiscussionServiceHealthCheck extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
