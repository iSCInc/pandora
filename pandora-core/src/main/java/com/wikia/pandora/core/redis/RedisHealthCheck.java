package com.wikia.pandora.core.redis;

import com.codahale.metrics.health.HealthCheck;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisHealthCheck extends HealthCheck {

    private final JedisPool pool;

    public RedisHealthCheck(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        try (Jedis jedis = pool.getResource()) {
            final String pong = jedis.ping();
            if ("PONG".equals(pong)) {
                return HealthCheck.Result.healthy();
            }
        }
        return HealthCheck.Result.unhealthy("Could not ping redis");
    }
}
