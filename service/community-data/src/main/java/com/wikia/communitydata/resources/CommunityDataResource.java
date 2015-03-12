package com.wikia.communitydata.resources;

import com.wikia.communitydata.configuration.MysqlConfiguration;
import com.wikia.communitydata.core.CommunityData;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import org.jooq.types.UShort;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/{domain}")
@Produces(MediaType.APPLICATION_JSON)
public class CommunityDataResource {
  private final MysqlConfiguration wikiDb;

  @Inject
  public CommunityDataResource(MysqlConfiguration wikiDb) {
    this.wikiDb = wikiDb;
  }

  @GET
  @Timed
  public CommunityData getData(@PathParam("domain") String domain) {
    return new CommunityData.Builder(wikiDb, domain).build();
  }
}
