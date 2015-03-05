package com.wikia.exampleservice.configuration;

import com.wikia.pandora.core.consul.ConsulConfig;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

public class ExampleConfiguration extends Configuration {

  @NotEmpty
  @NotNull
  private String greetingsWord; // value is set up by dropwizard reflection magic.
  private ConsulConfig consulConfig;

  public String getGreetingsWord() {
    return greetingsWord;
  }

  public ConsulConfig getConsulConfig() {
    return consulConfig;
  }
}