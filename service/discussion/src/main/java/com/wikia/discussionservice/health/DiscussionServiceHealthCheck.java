package com.wikia.discussionservice.health;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class DiscussionServiceHealthCheck extends InjectableHealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }

  @Override
  public String getName() {
    return "discussionservice health check";
  }
}
