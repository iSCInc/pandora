package com.wikia.exampleservice.configuration;

import com.wikia.pandora.core.consul.ConsulConfiguration;
import com.wikia.pandora.core.consul.ProvidesConsulConfiguration;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

public class ExampleServiceConfiguration extends Configuration implements ProvidesConsulConfiguration {

  @NotEmpty
  @NotNull
  private String greetingsWord; // value is set up by dropwizard reflection magic.
  private ConsulConfiguration consulConfiguration;

  public String getGreetingsWord() {
    return greetingsWord;
  }

  public ConsulConfiguration getConsulConfiguration() {
    return consulConfiguration;
  }
}
