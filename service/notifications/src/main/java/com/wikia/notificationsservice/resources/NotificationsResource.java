package com.wikia.notificationsservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.notificationsservice.configuration.NotificationsConfiguration;
import com.wikia.notificationsservice.domain.Notification;
import redis.clients.jedis.Jedis;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/notification/{name}")
@Produces(RepresentationFactory.HAL_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationsResource {

  private final RepresentationFactory representationFactory;
  private final NotificationsConfiguration configuration;

  public NotificationsResource(RepresentationFactory representationFactory, NotificationsConfiguration configuration) {
    this.representationFactory = representationFactory;
    this.configuration = configuration;
  }

    @GET
    @Timed
    public Response show(@PathParam("name") String name, @Context Jedis jedis) {
        String jedisValue;

        jedisValue = jedis.get(name);

        if (jedisValue == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .entity("Notification doesn't exist").build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(new Notification(jedisValue)).build();
    }

    @POST
    @Timed
    public Representation update(@PathParam("name") String name, @Valid Notification notification, @Context Jedis jedis) {
        jedis.set(name, notification.getText());

        Representation representation = representationFactory.newRepresentation();
        representation.withProperty("uri", UriBuilder.fromResource(NotificationsResource.class)
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
