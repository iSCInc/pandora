package com.wikia.pandora.core.jedis;

import org.junit.Test;
import redis.clients.jedis.JedisPool;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class JedisPoolManagerTest {
    @Test
    public void destroysManagedPool() throws Exception {
        JedisPool pool = mock(JedisPool.class);
        JedisPoolManager manager = new JedisPoolManager(pool);

        manager.stop();

        verify(pool).destroy();
    }

    @Test
    public void doesNothingOnStart() throws Exception {
        JedisPool pool = mock(JedisPool.class);
        new JedisPoolManager(pool).start();
        verifyZeroInteractions(pool);
    }
}