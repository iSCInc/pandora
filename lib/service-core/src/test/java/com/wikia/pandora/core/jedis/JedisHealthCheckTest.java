package com.wikia.pandora.core.jedis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import static com.wikia.pandora.core.jedis.testing.Subjects.healthCheck;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JedisHealthCheckTest {
    @Mock JedisPool pool;
    @Mock Jedis jedis;

    JedisHealthCheck healthcheck;

    @Before
    public void setup() {
        when(pool.getResource()).thenReturn(jedis);
        healthcheck = new JedisHealthCheck(pool);
    }

    @Test
    public void isHealthyWhenPingCompletes() throws Exception {
        when(jedis.ping()).thenReturn("PONG");
        assert_().about(healthCheck()).that(healthcheck.check()).isHealthy();
    }

    @Test
    public void isUnhealthyWhenPingFails() throws Exception {
        when(jedis.ping()).thenReturn("huh?");
        assert_().about(healthCheck()).that(healthcheck.check()).isUnhealthy();
    }

    @Test(expected = JedisException.class)
    public void doesNotCatchJedisExceptions() throws Exception {
        when(jedis.ping()).thenThrow(new JedisException("boom"));
        healthcheck.check();
    }

    @Test(expected = JedisConnectionException.class)
    public void doesNotCatchJedisPoolExceptions() throws Exception {
        when(pool.getResource()).thenThrow(new JedisConnectionException("nope."));
        healthcheck.check();
    }

    @Test
    public void returnsConnectionWhenComplete() throws Exception {
        when(jedis.ping()).thenReturn("PONG");
        healthcheck.check();
        verify(jedis).close();
    }

    @Test
    public void returnsConnectionWhenPingThrows() throws Exception {
        when(jedis.ping()).thenThrow(new JedisException("boom"));
        try {
            healthcheck.check();
            assert_().fail("expected a JedisException, got nothing");
        } catch (JedisException e) {
            verify(jedis).close();
        }
    }
}