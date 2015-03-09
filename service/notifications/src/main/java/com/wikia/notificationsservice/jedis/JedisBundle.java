package com.wikia.notificationsservice.jedis;

import com.wikia.notificationsservice.jedis.jersey.JedisPoolBinder;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;

public abstract class JedisBundle<T extends Configuration> implements ConfiguredBundle<T>, JedisConfiguration<T> {

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

        environment.healthChecks().register("redis", new JedisHealthCheck(pool));
        environment.jersey().register(new JedisPoolBinder(pool));

    }
}
