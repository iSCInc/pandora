package com.wikia.exampleservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wikia.pandora.core.jedis.JedisFactory;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ExampleConfiguration extends Configuration {

  @NotEmpty
  @NotNull
  private String greetingsWord; // value is set up by dropwizard reflection magic.

  @NotNull
  private JedisFactory redisFactory = new JedisFactory();

  public String getGreetingsWord() {
    return greetingsWord;
  }

  @JsonProperty("redis")
  public JedisFactory getJedisFactory() {
    return redisFactory;
  }

  @JsonProperty("redis")
  public void setJedisFactory(JedisFactory jedisFactory) {
    this.redisFactory = jedisFactory;
  }

}
