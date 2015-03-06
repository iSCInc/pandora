package com.wikia.exampleservice;

import com.google.inject.AbstractModule;

import com.wikia.exampleservice.configuration.ExampleServiceConfiguration;
import com.wikia.pandora.core.consul.ConsulModule;
import com.wikia.pandora.core.consul.ProvidesConsulConfiguration;

public class ExampleServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class)
        .to(ExampleServiceConfiguration.class);
    binder().install(new ConsulModule());
  }

}
