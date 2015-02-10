package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.mwapi.domain.Page;
import com.wikia.pandora.api.service.RevisionService;
import com.wikia.pandora.core.util.UriBuilder;
import com.wikia.pandora.domain.Revision;

import java.net.URI;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("{wikia}/revision")
@Produces(RepresentationFactory.HAL_JSON)

public class HalRevisionResource {

  private final RevisionService revisionService;
  private final RepresentationFactory representationFactory;

  public HalRevisionResource(RevisionService revisionService,
                             RepresentationFactory representationFactory) {

    this.revisionService = revisionService;
    this.representationFactory = representationFactory;
  }


  @GET
  @Path("/{revId}")
  @Timed
  public Object getRevision(@PathParam("wikia") String wikia,
                            @PathParam("revId") Long revId,
                            @QueryParam("content") @DefaultValue("false") Boolean withContent) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, revId, withContent);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, revId, withContent));

    Revision revision = revisionService.getRevisionById(wikia, revId, withContent);

    representation.withBean(revision);

    representation.withLink("parentRevision", getLinkToRevision(wikia, revision.getParentId()));
    representation.withLink("newerRevision", getLinkToRevision(wikia, revision.getLastRevId()));
    representation.withLink("user", getLinkToUser(wikia, revision.getUser()));
    representation.withLink("article", getLinkToArticle(wikia, revision.getTitle()));

    return representation;
  }

  private String getLinkToArticle(String wikia, String title) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/articles/{title}")
        .build(wikia, title).getPath();
  }

  private String getLinkToUser(String wikia, String userName) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/users/{name}")
        .build(wikia, userName).getPath();
  }

  private String getLinkToRevision(String wikia, int revId) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/revision/{revId}")
        .build(wikia, revId).getPath();
  }
}
