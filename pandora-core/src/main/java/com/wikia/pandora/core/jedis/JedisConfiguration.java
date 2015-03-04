package com.wikia.pandora.core.jedis;

import io.dropwizard.Configuration;

public interface JedisConfiguration<T extends Configuration> {
    JedisFactory getJedisFactory(T configuration);
}
