package com.wikia.exampleservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.exampleservice.configuration.ExampleConfiguration;
import redis.clients.jedis.Jedis;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

@Path("/notification/{name}")
@Produces(RepresentationFactory.HAL_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExampleResource {

  private final RepresentationFactory representationFactory;
  private final ExampleConfiguration configuration;

  public ExampleResource(RepresentationFactory representationFactory, ExampleConfiguration configuration) {
    this.representationFactory = representationFactory;
    this.configuration = configuration;
  }

    @GET
    @Timed
    public Notification show(@PathParam("name") String name, @Context Jedis jedis) {
        String jedisValue;

        jedisValue = jedis.get(name);

        return new Notification(jedisValue);
    }

    @POST
    @Timed
    public Representation update(@PathParam("name") String name, @Valid Notification notification, @Context Jedis jedis) {
        jedis.set(name, notification.getText());

        Representation representation = representationFactory.newRepresentation();
        representation.withProperty("uri", UriBuilder.fromResource(ExampleResource.class)
                .build(name));
        return representation;
    }

    @DELETE
    @Timed
    public Representation delete(@PathParam("name") String name, @Context Jedis jedis) {
        Representation representation = representationFactory.newRepresentation();

        jedis.del(name);

        representation.withProperty("status", "ok");
        return representation;
    }
}
