package com.wikia.notificationsservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.pandora.core.jedis.JedisFactory;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class NotificationsConfiguration extends Configuration implements ProvidesConsulConfiguration {

  @NotNull
  private JedisFactory jedisFactory = new JedisFactory();

  @Valid
  @JsonProperty
  private ConsulConfiguration consulConfiguration;

  @JsonProperty("redis")
  public JedisFactory getJedisFactory() {
    return this.jedisFactory;
  }

  @JsonProperty("redis")
  public void setJedisFactory(JedisFactory jedisFactory) {
    this.jedisFactory = jedisFactory;
  }

  public ConsulConfiguration getConsulConfiguration() {
    return consulConfiguration;
  }

  public void setConsulConfiguration(ConsulConfiguration consulConfiguration) {
    this.consulConfiguration = consulConfiguration;
  }
}
