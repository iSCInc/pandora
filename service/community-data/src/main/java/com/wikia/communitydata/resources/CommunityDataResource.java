package com.wikia.communitydata.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/community-data")
@Produces(RepresentationFactory.HAL_JSON)
public class CommunityDataResource {

  private final RepresentationFactory representationFactory;
  public CommunityDataResource(RepresentationFactory representationFactory) {
    this.representationFactory = representationFactory;
  }

  @GET
  @Path("/{name}")
  @Timed
  public Representation getHelloWorld(@PathParam("name") String name) {
    Representation representation = representationFactory.newRepresentation();

    representation.withProperty("Greeting", String.format("%s %s", "Hello,", name));
    return representation;
  }
}
