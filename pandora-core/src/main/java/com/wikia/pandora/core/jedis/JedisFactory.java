package com.wikia.pandora.core.jedis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.net.HostAndPort;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.validation.constraints.NotNull;
/**
 * A factory for creating configured {@link JedisPool} instances.
 *
 * Configuration Parameters
 * [Name: Default Value - Description]
 *
 * * endpoint [required]: localhost:6379 - The redis server's host and port, i.e. localhost:6379.
 *     If no port is given, the default redis port {@value #DEFAULT_PORT} is assumed.
 * * minIdle: 0 - The minimum number of idle connections to maintain in the connection pool.
 * * maxIdle: 0 - The maximum number of idle connections to maintain in the connection pool.
 * * maxTotal: {@value JedisFactory#DEFAULT_MAX_TOTAL} - The maximum number of connections allowed in the connection pool.
 *
 */
public class JedisFactory {
    public static final int DEFAULT_PORT = 6379;
    public static final int DEFAULT_MAX_TOTAL = 1024;
    @JsonProperty
    @NotNull
    private HostAndPort endpoint;
    @JsonProperty
    private int minIdle = 0;
    @JsonProperty
    private int maxIdle = 0;
    @JsonProperty
    private int maxTotal = DEFAULT_MAX_TOTAL;
    public HostAndPort getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(HostAndPort endpoint) {
        this.endpoint = endpoint;
    }
    public String getHost() {
        return endpoint.getHostText();
    }
    public int getPort() {
        return endpoint.getPortOrDefault(DEFAULT_PORT);
    }
    public int getMinIdle() {
        return minIdle;
    }
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
    public int getMaxIdle() {
        return maxIdle;
    }
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }
    public int getMaxTotal() {
        return maxTotal;
    }
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }
    public JedisPool build(Environment environment) {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(getMinIdle());
        poolConfig.setMaxIdle(getMaxIdle());
        poolConfig.setMaxTotal(getMaxTotal());
        final JedisPool pool = new JedisPool(poolConfig, getHost(), getPort());
        environment.lifecycle().manage(new JedisPoolManager(pool));
        return pool;
    }
}
