package com.wikia.exampleservice.guicemodule;

import com.google.inject.AbstractModule;

import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.exampleservice.ExampleServiceConfiguration;
import com.wikia.exampleservice.service.helloworld.HelloWorldService;
import com.wikia.exampleservice.service.helloworld.SimpleHelloWorldService;


public class ProdExampleModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ProvidesConsulConfiguration.class).to(ExampleServiceConfiguration.class);
    bind(HelloWorldService.class).to(SimpleHelloWorldService.class);
  }
}
