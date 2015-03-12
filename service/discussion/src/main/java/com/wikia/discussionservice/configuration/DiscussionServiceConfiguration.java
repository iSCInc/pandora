package com.wikia.discussionservice.configuration;

import com.wikia.discussionservice.services.JedisService;
import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import io.dropwizard.Configuration;
import redis.clients.jedis.Jedis;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DiscussionServiceConfiguration extends Configuration
  implements ProvidesConsulConfiguration {

  @NotNull
  @Valid
  private JedisService redis;

  private ConsulConfiguration consulConfiguration;

  public JedisService getRedis() {
    return redis;
  }

  public ConsulConfiguration getConsulConfiguration() {
    return consulConfiguration;
  }

}
