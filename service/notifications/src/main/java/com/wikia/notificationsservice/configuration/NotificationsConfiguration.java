package com.wikia.notificationsservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wikia.notificationsservice.jedis.JedisFactory;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class NotificationsConfiguration extends Configuration {

  @NotEmpty
  @NotNull
  private String greetingsWord; // value is set up by dropwizard reflection magic.

  @NotNull
  private JedisFactory jedisFactory = new JedisFactory();

  public String getGreetingsWord() {
    return greetingsWord;
  }

  @JsonProperty("redis")
  public JedisFactory getJedisFactory() {
    return this.jedisFactory;
  }

  @JsonProperty("redis")
  public void setJedisFactory(JedisFactory jedisFactory) {
    this.jedisFactory = jedisFactory;
  }

}
