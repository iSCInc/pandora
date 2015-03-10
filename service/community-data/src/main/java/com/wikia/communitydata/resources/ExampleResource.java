package com.wikia.communitydata.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import com.wikia.communitydata.domain.SimplePojo;
import com.wikia.communitydata.domain.builder.SimplePojoBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class ExampleResource {

  private final RepresentationFactory representationFactory;
  private String greetingsWord;

  public ExampleResource(RepresentationFactory representationFactory) {
    this.representationFactory = representationFactory;
  }

  @GET
  @Path("/HelloWorld/{name}")
  @Timed
  public Representation getHelloWorld(@PathParam("name") String name) {
    Representation representation = representationFactory.newRepresentation();

    representation.withProperty("Greetings", String.format("%s %s", getGreetingsWord(), name));
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

  public void setGreetingsWord(String greetingsWord) {
    this.greetingsWord = greetingsWord;
  }

  public String getGreetingsWord() {
    return greetingsWord;
  }
}
