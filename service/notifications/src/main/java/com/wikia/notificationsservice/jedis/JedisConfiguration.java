package com.wikia.notificationsservice.jedis;

import io.dropwizard.Configuration;

public interface JedisConfiguration<T extends Configuration> {
    JedisFactory getJedisFactory(T configuration);
}
