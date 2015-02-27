package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
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

  @GET
  @Path("/{siteId}/forums")
  @Timed
  public Representation getForums(@NotNull @PathParam("siteId") IntParam siteId,
                                  @QueryParam("responseGroup") @DefaultValue("small")
                                  String requestedResponseGroup,
                                  @Context UriInfo uriInfo) {

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<ForumRoot> forumRoot = forumService.getForums(siteId.get());

    Representation representation = forumRoot.map(
        root -> forumMapper.buildRepresentation(siteId.get(), root, uriInfo, responseGroup))
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
                                 String requestedResponseGroup,
                                 @Context UriInfo uriInfo) {
    Preconditions.checkArgument(id.get() >= 1,
        "Offset was %s but expected 1 or greater", id.get());

    Optional<Forum> forum = forumService.getForum(siteId.get(), id.get(), offset.get(), limit.get());

    if (forum.isPresent()) {
      ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
      Preconditions.checkNotNull(responseGroup, "Invalid response group");

      return forumMapper.buildRepresentation(siteId.get(), forum.get(), uriInfo, responseGroup);
    }

    //TODO: Return a proper 404 error when no forum is found
    return null;
  }

  @POST
  @Path("/{siteId}/forum")
  @Timed
  public Representation createForum(@NotNull @PathParam("siteId") IntParam siteId,
                                    @Context HttpServletRequest request,
                                    @Context UriInfo uriInfo) {
    Optional<Forum> forum = Optional.empty();
    try {
      HashMap<String, Object> result =
          new ObjectMapper().readValue(
              new BufferedReader(new InputStreamReader(
                  request.getInputStream())), HashMap.class);

      forum = forumService.createForum(siteId.get(),
          (int) result.getOrDefault("parentId", 1),
          (String) result.getOrDefault("name", "default name"));

    } catch (IOException e) {
      // TODO: Log and throw 404
    }

    if (forum.isPresent()) {
      return forumMapper.buildRepresentation(siteId.get(), forum.get(), uriInfo);
    }

    // TODO: Log and throw 404
    return null;
  }


}
