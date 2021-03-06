package com.wikia.exampleservice;

import com.google.inject.AbstractModule;

import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.exampleservice.configuration.ExampleServiceConfiguration;

public class ExampleServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
        .to(ExampleServiceConfiguration.class);
    binder().install(new ConsulModule());
  }

}
