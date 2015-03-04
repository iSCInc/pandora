package com.wikia.pandora.core.redis;

import com.wikia.pandora.core.redis.jersey.JedisFactory;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;

public abstract class RedisBundle<T extends Configuration> implements ConfiguredBundle<T>, RedisConfiguration<T> {

    private JedisPool pool;

    public JedisPool getPool() {
        return pool;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    @Override
    public void run(T appConfiguration, Environment environment) throws Exception {
        pool = getJedisFactory(appConfiguration).build(environment);

        environment.healthChecks().register("redis", new RedisHealthCheck(pool));
        environment.jersey().register(new JedisFactory(pool));
    }
}
