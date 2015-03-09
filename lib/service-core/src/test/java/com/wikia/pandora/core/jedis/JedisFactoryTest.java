package com.wikia.pandora.core.jedis;

import com.google.common.net.HostAndPort;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import redis.clients.jedis.JedisPool;

import static com.wikia.pandora.core.jedis.testing.Subjects.jedisFactory;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JedisFactoryTest {
    @Mock Environment environment;
    @Mock JerseyEnvironment jerseyEnvironment;
    @Mock LifecycleEnvironment lifecycleEnvironment;

    JedisFactory factory;

    @Before
    public void setup() {
        when(environment.lifecycle()).thenReturn(lifecycleEnvironment);
        factory = new JedisFactory();
    }

    @Test
    public void assumesDefaultPortIfNoneGiven() {
        factory.setEndpoint(HostAndPort.fromString("localhost"));

        assert_().about(jedisFactory()).that(factory).hasDefaultRedisPort();
    }

    @Test
    public void getsHostAndPortFromEndpoint() {
        factory.setEndpoint(HostAndPort.fromString("127.0.0.2:11211"));

        assert_().about(jedisFactory()).that(factory).hasHost("127.0.0.2");
        assert_().about(jedisFactory()).that(factory).hasPort(11211);
    }

    @Test
    public void managesCreatedJedisPool() {
        factory.setEndpoint(HostAndPort.fromString("localhost"));
        factory.build(environment);

        ArgumentCaptor<JedisPoolManager> managerCaptor = ArgumentCaptor.forClass(JedisPoolManager.class);
        verify(lifecycleEnvironment).manage(managerCaptor.capture());

        assertThat(managerCaptor.getValue()).isNotNull();
    }

    @Test
    public void setsDefaultIdleAndTotalConnections() {
        assert_().about(jedisFactory()).that(factory).hasDefaultMinIdleConnections();
        assert_().about(jedisFactory()).that(factory).hasDefaultMaxIdleConnections();
        assert_().about(jedisFactory()).that(factory).hasDefaultTotalConnections();
    }
}