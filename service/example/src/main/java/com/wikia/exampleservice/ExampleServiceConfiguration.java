package com.wikia.exampleservice;

import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;

public class ExampleServiceConfiguration extends Configuration implements
                                                               ProvidesConsulConfiguration {

  private ConsulConfiguration consulConfiguration;

  @NotEmpty
  @NotNull
  private String greetingsWord;

  public String getGreetingsWord() {
    return greetingsWord;
  }

  public ConsulConfiguration getConsulConfiguration() {
    return consulConfiguration;
  }
}
