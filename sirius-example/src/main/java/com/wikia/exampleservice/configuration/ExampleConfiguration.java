package com.wikia.exampleservice.configuration;

import com.bendb.dropwizard.redis.JedisFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ExampleConfiguration extends Configuration {

  @NotEmpty
  @NotNull
  private String greetingsWord; // value is set up by dropwizard reflection magic.

  @NotNull
  private JedisFactory redis = new JedisFactory();

  public String getGreetingsWord() {
    return greetingsWord;
  }

  @JsonProperty("redis")
  public JedisFactory getJedisFactory() {
    return redis;
  }

  @JsonProperty("redis")
  public void setJedisFactory(JedisFactory jedisFactory) {
    this.redis = jedisFactory;
  }

}
