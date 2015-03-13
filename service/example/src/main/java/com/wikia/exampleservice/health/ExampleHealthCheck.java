package com.wikia.exampleservice.health;

import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class ExampleHealthCheck extends InjectableHealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }

  @Override
  public String getName() {
    return "example";
  }
}
