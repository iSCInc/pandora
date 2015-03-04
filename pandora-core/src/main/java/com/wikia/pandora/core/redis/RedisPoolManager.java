package com.wikia.pandora.core.redis;

import io.dropwizard.lifecycle.Managed;
import redis.clients.jedis.JedisPool;

public class RedisPoolManager implements Managed {
    private final JedisPool pool;
    public RedisPoolManager(JedisPool pool) {
        this.pool = pool;
    }
    @Override
    public void start() throws Exception {
// sure, no problem
    }
    @Override
    public void stop() throws Exception {
        pool.destroy();
    }
}