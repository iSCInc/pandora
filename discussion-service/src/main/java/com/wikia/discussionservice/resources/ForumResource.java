package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.ForumRepresentationMapper;
import com.wikia.discussionservice.services.ForumService;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import java.util.Optional;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class ForumResource {

  @NonNull
  final private ForumService forumService;

  @NonNull
  final private ForumRepresentationMapper forumMapper;
  
  @Inject
  public ForumResource(ForumService forumService, ForumRepresentationMapper forumMapper) {
    this.forumService = forumService;
    this.forumMapper = forumMapper;
  }
  
  /**
   * Only to satisfy the compiler, DI will ensure that
   * ForumService and RepresentationFactory are not null
   */
  private ForumResource() {
    this.forumService = null;
    this.forumMapper = null;
  }

  @GET
  @Path("/{siteId}/forums")
  @Timed
  public Representation getForums(@NotNull @PathParam("siteId") IntParam siteId,
                                  @QueryParam("limit") @DefaultValue("10") IntParam limit,
                                  @QueryParam("offset") @DefaultValue("1") IntParam offset,
                                  @QueryParam("responseGroup") @DefaultValue("small") 
                                  String requestedResponseGroup) {

    Preconditions.checkArgument(offset.get() >= 1,
        "Offset was %s but expected 1 or greater", offset.get());

    Preconditions.checkArgument(limit.get() >= 1,
        "Limit was %s but expected 1 or greater", limit.get());

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<ForumRoot> forumRoot = forumService.getForums(siteId.get(), offset.get(), limit.get());

    Representation representation = forumRoot.map(
        root -> forumMapper.buildRepresentation(siteId.get(), root, responseGroup))
          .orElseThrow(IllegalArgumentException::new);

    return representation;
  }

  @GET
  @Path("/{siteId}/forum/{id}")
  @Timed
  public Representation getForum(@NotNull @PathParam("siteId") IntParam siteId,
                                 @NotNull @PathParam("id") IntParam id,
                                 @QueryParam("limit") @DefaultValue("10") IntParam limit,
                                 @QueryParam("offset") @DefaultValue("1") IntParam offset,
                                 @QueryParam("responseGroup") @DefaultValue("small")
                                 String requestedResponseGroup) {
    Preconditions.checkArgument(id.get() >= 1,
        "Offset was %s but expected 1 or greater", id.get());
    
    Forum forum = forumService.getForum(siteId.get(), id.get(), offset.get(), limit.get());

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    return forumMapper.buildRepresentation(siteId.get(), forum, responseGroup);
  }
  


}
