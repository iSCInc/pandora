package com.wikia.jedis;

import com.codahale.metrics.health.HealthCheck;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHealthCheck extends HealthCheck {

  private final JedisPool pool;

  public JedisHealthCheck(JedisPool pool) {
    this.pool = pool;
  }

  @Override
  protected HealthCheck.Result check() throws Exception {
    try (Jedis jedis = pool.getResource()) {
      final String answer = jedis.ping();
      if (answer.equals("PONG")) {
        return HealthCheck.Result.healthy();
      }
    }
    return HealthCheck.Result.unhealthy("Could not ping redis");
  }
}
