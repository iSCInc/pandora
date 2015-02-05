package com.wikia.exampleservice.configuration;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

public class ExampleConfiguration extends Configuration {

  @NotEmpty
  @NotNull
  
  ///value is set up by dropwizard reflection magic.
  private String greetingsWord;

  public String getGreetingsWord() {
    return greetingsWord;
  }
}
