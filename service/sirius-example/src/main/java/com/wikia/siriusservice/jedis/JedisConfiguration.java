package com.wikia.siriusservice.jedis;

import io.dropwizard.Configuration;

public interface JedisConfiguration<T extends Configuration> {
    JedisFactory getJedisFactory(T configuration);
}
