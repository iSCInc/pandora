package com.wikia.exampleservice.configuration;

import com.wikia.pandora.core.consul.ConsulConfig;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

public class ExampleConfiguration extends Configuration {

  @NotEmpty
  @NotNull
  private String greetingsWord; // value is set up by dropwizard reflection magic.
  private ConsulConfig consulConfig;
  private String storageHost;

  public String getGreetingsWord() {
    return greetingsWord;
  }

  public ConsulConfig getConsulConfig() {
    return consulConfig;
  }

  public String getStorageHost() {
    return storageHost;
  }

}

