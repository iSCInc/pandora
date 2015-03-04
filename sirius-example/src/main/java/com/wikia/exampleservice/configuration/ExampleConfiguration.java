package com.wikia.exampleservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wikia.pandora.core.redis.RedisFactory;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ExampleConfiguration extends Configuration {

  @NotEmpty
  @NotNull
  private String greetingsWord; // value is set up by dropwizard reflection magic.

  @NotNull
  private RedisFactory redis = new RedisFactory();

  public String getGreetingsWord() {
    return greetingsWord;
  }

  @JsonProperty("redis")
  public RedisFactory getJedisFactory() {
    return redis;
  }

  @JsonProperty("redis")
  public void setJedisFactory(RedisFactory jedisFactory) {
    this.redis = jedisFactory;
  }

}
