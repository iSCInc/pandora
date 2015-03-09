package com.wikia.pandora.core.jedis;

import io.dropwizard.lifecycle.Managed;
import redis.clients.jedis.JedisPool;

public class JedisPoolManager implements Managed {
    private final JedisPool pool;
    public JedisPoolManager(JedisPool pool) {
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
