package com.wikia.communitydata.resources;

import com.wikia.communitydata.core.CommunityData;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import org.jooq.DSLContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/{domain}")
@Api("/")
@Produces(MediaType.APPLICATION_JSON)
public class CommunityDataResource {
  @GET
  @Timed
  public CommunityData getData(@PathParam("domain") String domain, @Context DSLContext db) {
    return new CommunityData.Builder(db, domain).build();
  }
}
