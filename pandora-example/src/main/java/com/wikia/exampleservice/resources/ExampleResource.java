package com.wikia.exampleservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.exampleservice.domain.SimplePojo;
import com.wikia.exampleservice.domain.builder.SimplePojoBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class ExampleResource {

  private final RepresentationFactory representationFactory;

  public ExampleResource(RepresentationFactory representationFactory) {
    this.representationFactory = representationFactory;
  }

  @GET
  @Path("/HelloWorld/{name}")
  @Timed
  public Representation getHelloWorld(@PathParam("name") String name) {
    Representation representation = representationFactory.newRepresentation();

    representation.withProperty("Greatings", name);
    return representation;
  }

  @GET
  @Path("/SimplePojo/{id}/{name}/{bool}")
  @Timed

  public Representation getSimplePojo(
      @PathParam("id") int id,
      @PathParam("name") String name,
      @PathParam("bool") boolean bool) {
    Representation representation = representationFactory.newRepresentation();

    SimplePojo pojo = SimplePojoBuilder.aSimplePojo()
        .withId(id)
        .withSomeBool(bool)
        .withSomeString(name)
        .build();
    representation.withBean(pojo);
    return representation;
  }
}
