package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import com.wikia.discussionservice.domain.ErrorResponse;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.ThreadRepresentationMapper;
import com.wikia.discussionservice.services.ForumService;
import com.wikia.discussionservice.services.PostService;
import com.wikia.discussionservice.services.ThreadService;
import com.wikia.discussionservice.utils.ErrorResponseBuilder;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("/")
@Api(basePath = "/Threads", value = "Threads", consumes = MediaType.APPLICATION_JSON,
    produces = RepresentationFactory.HAL_JSON, description = "APIs pertaining to Threads")
@Produces(RepresentationFactory.HAL_JSON)
public class ThreadResource {

  @NonNull
  final private ThreadService threadService;

  @NonNull
  final private ForumService forumService;

  @NonNull
  final private PostService postService;

  @NonNull
  final private ThreadRepresentationMapper threadMapper;

  @Inject
  public ThreadResource(ThreadService threadService,
                        ForumService forumService,
                        PostService postService,
                        ThreadRepresentationMapper threadMapper) {
    this.threadService = threadService;
    this.forumService = forumService;
    this.postService = postService;
    this.threadMapper = threadMapper;
  }

  @GET
  @Path("/{siteId}/threads/{threadId}")
  @Produces(RepresentationFactory.HAL_JSON)
  @ApiOperation(value = "Get a specific thread for a site",
      notes = "Returns threads details and a list of posts.",
      response = ForumThread.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful retrieval of thread", response = ForumRoot.class),
      @ApiResponse(code = 404, message = "No thread found", response = ErrorResponse.class)})
  @Timed
  public Response getThread(
      @ApiParam(value = "The id of the site to list the forums") @NotNull @PathParam("siteId") IntParam siteId,
      @ApiParam(value = "The id of a specific thread") @NotNull @PathParam("threadId") IntParam threadId,
      @ApiParam(value = "The number of posts to return with this call") @QueryParam("limit") @DefaultValue("10") IntParam limit,
      @ApiParam(value = "The pagination position") @QueryParam("offset") @DefaultValue("1") IntParam offset,
      @ApiParam(value = "The responseGroup controls the level of details returned with this call", allowableValues = "small, large") @QueryParam("responseGroup") @DefaultValue("small") String requestedResponseGroup,
      @Context UriInfo uriInfo) {

    Preconditions.checkArgument(offset.get() >= 1,
        "Offset was %s but expected 1 or greater", offset.get());

    Preconditions.checkArgument(limit.get() >= 1,
        "Limit was %s but expected 1 or greater", limit.get());

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<ForumThread> forumThread = threadService.getForumThread(siteId.get(), threadId.get(),
        offset.get(), limit.get());

    if (forumThread.isPresent()) {
      Representation representation = threadMapper.buildRepresentation(
          siteId.get(), forumThread.get(), uriInfo, responseGroup);

      return Response.ok(representation).build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
        "Thread not found for site id: %s and thread id: %s", siteId.get(),
        threadId.get()), null, Response.Status.NOT_FOUND);
  }

  @POST
  @Path("/{siteId}/forums/{forumId}/threads")
  @Produces(RepresentationFactory.HAL_JSON)
  @ApiOperation(value = "Start a new thread for the forum",
      notes = "Returns the newly created thread.",
      response = ForumThread.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful creation of a thread", response = ForumThread.class),
      @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)})
  @Timed
  public Response startThread(
      @ApiParam(value = "The id of the site to list the forums") @NotNull @PathParam("siteId") IntParam siteId,
      @ApiParam(value = "The id of a specific forum") @NotNull @PathParam("forumId") IntParam forumId,
      @Valid Post post,
      @Context HttpServletRequest request,
      @Context UriInfo uriInfo) {
    Optional<Forum> forum = forumService.getForum(siteId.get(), forumId.get());

    if (!forum.isPresent()) {
      return ErrorResponseBuilder.buildErrorResponse(1234,
          String.format("No forum found for site id: %s with forum id: %s", siteId.get(),
              forumId.get()), null, Response.Status.NOT_FOUND);
    }

    Optional<ForumThread> createdThread = threadService.createThread(siteId.get(),
        forumId.get(), post);

    if (createdThread.isPresent()) {
      forum.get().getThreads().add(createdThread.get());

      Representation representation = threadMapper.buildRepresentation(siteId.get(),
          createdThread.get(), uriInfo);

      return Response.status(Response.Status.CREATED)
          .entity(representation)
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
            "Unable to create thread for site id: %s", siteId.get()),
        null, Response.Status.BAD_REQUEST);
  }
}
