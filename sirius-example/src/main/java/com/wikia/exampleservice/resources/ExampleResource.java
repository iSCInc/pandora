package com.wikia.exampleservice.resources;

import com.wikia.exampleservice.configuration.ExampleConfiguration;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

@Path("/notification/{name}")
@Produces(RepresentationFactory.HAL_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExampleResource {

  private final RepresentationFactory representationFactory;
  private final ExampleConfiguration configuration;
  private final String storageHost;

  public ExampleResource(RepresentationFactory representationFactory, ExampleConfiguration configuration) {
    this.representationFactory = representationFactory;
    this.configuration = configuration;
    this.storageHost = configuration.getStorageHost();
  }

    @GET
    @Timed
    public Notification show(@PathParam("name") String name) {
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), storageHost);
        String jedisValue;
        try (Jedis jedis = jedisPool.getResource()) {
            jedisValue = jedis.get(name);
        }
        jedisPool.destroy();

        return new Notification(jedisValue);
    }

    @POST
    @Timed
    public Representation update(@PathParam("name") String name, @Valid Notification notification) {

        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), storageHost);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(name, notification.getText());
        }
        jedisPool.destroy();


        Representation representation = representationFactory.newRepresentation();
        representation.withProperty("uri", UriBuilder.fromResource(ExampleResource.class)
                .build(name));
        return representation;
    }


    @DELETE
    @Timed
    public Representation delete(@PathParam("name") String name) {
        Representation representation = representationFactory.newRepresentation();


        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), storageHost);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(name);
        }
        jedisPool.destroy();

        representation.withProperty("status", "ok");
        return representation;
    }
}
