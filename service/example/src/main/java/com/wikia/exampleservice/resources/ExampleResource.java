package com.wikia.exampleservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.exampleservice.ExampleServiceApplication;
import com.wikia.exampleservice.domain.SimplePojo;
import com.wikia.exampleservice.domain.builder.SimplePojoBuilder;
import com.wikia.exampleservice.service.helloworld.HelloWorldService;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.dropwizard.jersey.caching.CacheControl;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class ExampleResource {

  private HelloWorldService helloWorldService;

  @Inject
  public ExampleResource(HelloWorldService helloWorldService) {
    this.helloWorldService = helloWorldService;
  }

  @GET
  @Path("/HelloWorld/{name}")
  @Timed
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.SECONDS)
  public Representation getHelloWorld(@PathParam("name") String name) {

    String welcomeMessage = helloWorldService.getWelcomeMessage(name);

    Representation
        representation =
        ExampleServiceApplication.REPRESENTATION_FACTORY.newRepresentation();

    representation.withProperty("Greetings", welcomeMessage);
    return representation;
  }

  @GET
  @Path("/SimplePojo/{id}/{name}/{bool}")
  @Timed
  public Representation getSimplePojo(
      @PathParam("id") int id,
      @PathParam("name") String name,
      @PathParam("bool") boolean bool) {
    Representation
        representation =
        ExampleServiceApplication.REPRESENTATION_FACTORY.newRepresentation();

    SimplePojo pojo = SimplePojoBuilder.createSimplePojo()
        .withId(id)
        .withSomeBool(bool)
        .withSomeString(name)
        .build();
    representation.withBean(pojo);
    return representation;
  }
}
