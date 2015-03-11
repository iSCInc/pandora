package com.wikia.discussionservice.resources;

import com.wikia.discussionservice.domain.ErrorResponse;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.ForumRepresentationMapper;
import com.wikia.discussionservice.services.ForumService;
import com.wikia.discussionservice.utils.ErrorResponseBuilder;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
@Api(basePath = "/Forums", value = "Forums", consumes = MediaType.APPLICATION_JSON,
    produces = RepresentationFactory.HAL_JSON, description = "APIs pertaining to Forums")
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
  @Produces(RepresentationFactory.HAL_JSON)
  @ApiOperation(value = "Returns all the forums for a site", notes = "Returns a complete list of forums",
      response = ForumRoot.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful retrieval of forum", response = ForumRoot.class),
      @ApiResponse(code = 404, message = "No forums found", response = ErrorResponse.class)})
  @Timed
  public Response getForums(
      @ApiParam(value = "The id of the site to list the forums") @NotNull @PathParam("siteId") IntParam siteId,
      @ApiParam(value = "The responseGroup controls the level of details returned with this call", allowableValues = "small, large") @QueryParam("responseGroup") @DefaultValue("small") String requestedResponseGroup,
      @Context UriInfo uriInfo) {
    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<ForumRoot> forumRoot = forumService.getForums(siteId.get());

    if (forumRoot.isPresent()) {
      Representation representation = forumMapper.buildRepresentation(siteId.get(),
                                                                      forumRoot.get(), uriInfo,
                                                                      responseGroup);
      return Response.ok(representation)
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
                                                       "No forums site id: %s", siteId.get()),
                                                   null, Response.Status.NOT_FOUND);
  }

  @GET
  @Path("/{siteId}/forums/{forumId}")
  @Produces(RepresentationFactory.HAL_JSON)
  @ApiOperation(value = "Get a specific forum for a site",
      notes = "Returns forum details either a list of subforums or threads. Use responseGroups to control the amount of details.",
      response = ForumRoot.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful retrieval of a forum", response = Forum.class),
      @ApiResponse(code = 404, message = "No forum found", response = ErrorResponse.class)})
  @Timed
  public Response getForum(
      @ApiParam(value = "The id of the site to list the forums") @NotNull @PathParam("siteId") IntParam siteId,
      @ApiParam(value = "The id of a specific forum") @NotNull @PathParam("forumId") IntParam forumId,
      @ApiParam(value = "The number of threads to return with this call") @QueryParam("limit") @DefaultValue("10") IntParam limit,
      @ApiParam(value = "The pagination position") @QueryParam("offset") @DefaultValue("1") IntParam offset,
      @ApiParam(value = "The responseGroup controls the level of details returned with this call", allowableValues = "small, large") @QueryParam("responseGroup") @DefaultValue("small") String requestedResponseGroup,
      @Context UriInfo uriInfo) {
    Preconditions.checkArgument(forumId.get() >= 1,
                                "Offset was %s but expected 1 or greater", forumId.get());

    Optional<Forum> forum = forumService.getForum(siteId.get(), forumId.get(),
                                                  offset.get(), limit.get());

    if (forum.isPresent()) {
      ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
      Preconditions.checkNotNull(responseGroup, "Invalid response group");

      Representation representation = forumMapper.buildRepresentation(siteId.get(),
                                                                      forum.get(), uriInfo,
                                                                      responseGroup);
      return Response.ok(representation)
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "No forum found for site id: %s with forum id: %s", siteId.get(),
        forumId.get()), null, Response.Status.NOT_FOUND);
  }

  @POST
  @Path("/{siteId}/forums")
  @Produces(RepresentationFactory.HAL_JSON)
  @ApiOperation(value = "Create a new forum for a site", response = ForumRoot.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successful creation of a forum", response = Forum.class),
      @ApiResponse(code = 400, message = "No forum for the site", response = ErrorResponse.class)})
  @Timed
  public Response createForum(
      @ApiParam(value = "The id of the site to list the forums") @NotNull @PathParam("siteId") IntParam siteId,
      @Valid Forum forum,
      @Context HttpServletRequest request,
      @Context UriInfo uriInfo) {
    // TODO: perform validation
    Optional<Forum> createdForum = forumService.createForum(siteId.get(), forum);

    if (createdForum.isPresent()) {
      Representation representation =
          forumMapper.buildRepresentation(siteId.get(), createdForum.get(), uriInfo);

      return Response.status(Response.Status.CREATED)
          .entity(representation)
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, "Unable to create forum",
                                                   null, Response.Status.BAD_REQUEST);
  }

  @DELETE
  @Path("/{siteId}/forums/{forumId}")
  @Produces(RepresentationFactory.HAL_JSON)
  @ApiOperation(value = "Delete a forum for a site")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Successful deletion of a forum"),
      @ApiResponse(code = 404, message = "No forum for the site", response = ErrorResponse.class)})
  @Timed
  public Response deleteForum(
      @ApiParam(value = "The id of the site to list the forums") @NotNull @PathParam("siteId") IntParam siteId,
      @ApiParam(value = "The id of a specific forum") @NotNull @PathParam("forumId") IntParam forumId,
      @Context HttpServletRequest request,
      @Context UriInfo uriInfo) {
    // TODO: perform validation
    Optional<Forum> deletedForum = forumService.deleteForum(siteId.get(), forumId.get());

    if (deletedForum.isPresent()) {
      return Response.noContent().build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "No forum found for site id: %s with forum id: %s", siteId.get(),
        forumId.get()), null, Response.Status.NOT_FOUND);
  }

  @PUT
  @Path("/{siteId}/forums")
  @Produces(RepresentationFactory.HAL_JSON)
  @ApiOperation(value = "Update a forum for a site", notes = "If the forum does not exist it will be created")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Successful creation of a forum", response = Forum.class),
      @ApiResponse(code = 204, message = "Successful update of a forum"),
      @ApiResponse(code = 404, message = "No forum for the site", response = ErrorResponse.class)})
  @Timed
  public Response updateForum(
      @ApiParam(value = "The id of the site to list the forums") @NotNull @PathParam("siteId") IntParam siteId,
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
        return Response.status(Response.Status.CREATED)
            .entity(representation)
            .build();
      }
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "Forum not found for site id: %s and forum id: %s", siteId.get(),
        forum.getId()), null, Response.Status.NOT_FOUND);
  }
}
