package com.wikia.communitydata.resources;

import com.wikia.communitydata.core.CommunityData;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.jooq.DSLContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/{domain}")
@Api("/CommunityData")
@Produces(MediaType.APPLICATION_JSON)
public class CommunityDataResource {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value="get information about a community",
      notes="get information about a community",
      produces=MediaType.APPLICATION_JSON
  )
  @ApiResponses(value={
      @ApiResponse(code=200, response=CommunityData.class, message="community data")
  })
  @Timed
  public CommunityData getData(@PathParam("domain") String domain, @Context DSLContext db) {
    return new CommunityData.Builder(db, domain).build();
  }
}
