package com.wikia.discussionservice.configuration;

import com.bendb.dropwizard.redis.JedisConfiguration;
import com.bendb.dropwizard.redis.JedisFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.wikia.discussionservice.services.JedisService;
import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import io.dropwizard.Configuration;
import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Jedis;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Context;

public class DiscussionServiceConfiguration extends Configuration
  implements ProvidesConsulConfiguration {
//  implements JedisConfiguration<DiscussionServiceConfiguration>, ProvidesConsulConfiguration {

//  @NotNull
//  @JsonProperty
//  private JedisFactory redis;
//
//  @Valid
//  @JsonProperty
//  private ConsulConfiguration consulConfiguration;
//
//  public JedisFactory getJedisFactory(DiscussionServiceConfiguration configuration) {
//    return redis;
//  }
//
////  public JedisFactory getJedisFactory() {
////    return new JedisFactory();
////  }
//
//  public ConsulConfiguration getConsulConfiguration() {
//    return consulConfiguration;
//  }

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
