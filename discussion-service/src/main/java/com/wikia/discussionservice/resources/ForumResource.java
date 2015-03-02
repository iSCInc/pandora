package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.ErrorResponse;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.ForumRepresentationMapper;
import com.wikia.discussionservice.services.ForumService;
import com.wikia.discussionservice.utils.ErrorResponseBuilder;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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
  public Response getForums(@NotNull @PathParam("siteId") IntParam siteId,
                                  @QueryParam("responseGroup") @DefaultValue("small")
                                  String requestedResponseGroup,
                                  @Context UriInfo uriInfo) {

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<ForumRoot> forumRoot = forumService.getForums(siteId.get());

    if (forumRoot.isPresent()) {
      Representation representation = forumMapper.buildRepresentation(siteId.get(), 
          forumRoot.get(), uriInfo, responseGroup);
      return Response.ok(representation)
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "No forums site id: %s with forum id: %s", siteId.get()),
        null, Response.Status.NOT_FOUND);
  }

  @GET
  @Path("/{siteId}/forums/{forumId}")
  @Timed
  public Response getForum(@NotNull @PathParam("siteId") IntParam siteId,
                                 @NotNull @PathParam("forumId") IntParam forumId,
                                 @QueryParam("limit") @DefaultValue("10") IntParam limit,
                                 @QueryParam("offset") @DefaultValue("1") IntParam offset,
                                 @QueryParam("responseGroup") @DefaultValue("small")
                                 String requestedResponseGroup,
                                 @Context UriInfo uriInfo) {
    Preconditions.checkArgument(forumId.get() >= 1,
        "Offset was %s but expected 1 or greater", forumId.get());

    Optional<Forum> forum = forumService.getForum(siteId.get(), forumId.get(), 
        offset.get(), limit.get());

    if (forum.isPresent()) {
      ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
      Preconditions.checkNotNull(responseGroup, "Invalid response group");

      Representation representation = forumMapper.buildRepresentation(siteId.get(), 
          forum.get(), uriInfo, responseGroup);
      return Response.ok(representation)
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "No forum found for site id: %s with forum id: %s", siteId.get(),
          forumId.get()), null, Response.Status.NOT_FOUND);
  }

  @POST
  @Path("/{siteId}/forums")
  @Timed
  public Response createForum(@NotNull @PathParam("siteId") IntParam siteId,
                                    @Valid Forum forum,
                                    @Context HttpServletRequest request,
                                    @Context UriInfo uriInfo) {
    // TODO: perform validation
    Optional<Forum> createdForum = forumService.createForum(siteId.get(), forum);

    if (createdForum.isPresent()) {
      Representation representation = 
          forumMapper.buildRepresentation(siteId.get(), createdForum.get(), uriInfo);
      
      return Response.created(
          URI.create(representation.getLinkByRel("self").getHref()))
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, "Unable to create forum",
        null, Response.Status.BAD_REQUEST);
  }

  @DELETE
  @Path("/{siteId}/forums/{forumId}")
  @Timed
  public Response deleteForum(@NotNull @PathParam("siteId") IntParam siteId,
                              @NotNull @PathParam("forumId") IntParam forumId,
                              @Context HttpServletRequest request,
                              @Context UriInfo uriInfo) {
    try {
      // TODO: perform validation
      Optional<Forum> deletedForum = forumService.deleteForum(siteId.get(), forumId.get());

      if (deletedForum.isPresent()) {
        return Response.noContent().build();
      }
    } catch (IllegalArgumentException iae) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "No forum found for site id: %s with forum id: %s", siteId.get(),
        forumId.get()), null, Response.Status.NOT_FOUND);
  }

  @PUT
  @Path("/{siteId}/forums")
  @Timed
  public Response updateForum(@NotNull @PathParam("siteId") IntParam siteId,
                              @Valid Forum forum,
                              @Context HttpServletRequest request,
                              @Context UriInfo uriInfo) {
    // TODO: perform validation


    Optional<Forum> updatedForum = forumService.updateForum(siteId.get(), forum);

    if (updatedForum.isPresent()) {
      if (updatedForum.get().getId() == forum.getId()) {
        return Response.noContent().build();
      } else {
        Representation representation = 
            forumMapper.buildRepresentation(siteId.get(), updatedForum.get(), uriInfo);
        return Response.created(URI.create(representation.getLinkByRel("self").getHref()))
            .build();
      }
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "Forum not found for site id: %s and forum id: %s", siteId.get(), 
        forum.getId()), null, Response.Status.NOT_FOUND);
  }
}
