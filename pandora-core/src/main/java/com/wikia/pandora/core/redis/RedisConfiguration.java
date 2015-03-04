package com.wikia.pandora.core.redis;

import io.dropwizard.Configuration;

public interface RedisConfiguration<T extends Configuration> {
    RedisFactory getJedisFactory(T configuration);
}