package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.ThreadRepresentationMapper;
import com.wikia.discussionservice.services.ForumService;
import com.wikia.discussionservice.services.PostService;
import com.wikia.discussionservice.services.ThreadService;
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
import java.util.Optional;

@Path("/")
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
  @Timed
  public Response getThread(@NotNull @PathParam("siteId") IntParam siteId,
                            @NotNull @PathParam("threadId") IntParam threadId,
                            @QueryParam("limit") @DefaultValue("10") IntParam limit,
                            @QueryParam("offset") @DefaultValue("1") IntParam offset,
                            @QueryParam("responseGroup") @DefaultValue("small")
                            String requestedResponseGroup,
                            @Context UriInfo uriInfo) {

    Preconditions.checkArgument(offset.get() >= 1,
        "Offset was %s but expected 1 or greater", offset.get());

    Preconditions.checkArgument(limit.get() >= 1,
        "Limit was %s but expected 1 or greater", limit.get());

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<ForumThread> forumThread = threadService.getForumThread(siteId.get(), threadId.get(),
        offset.get(), limit.get());

    if (forumThread != null) {
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
  @Timed
  public Response startThread(@NotNull @PathParam("siteId") IntParam siteId,
                              @NotNull @PathParam("forumId") IntParam forumId,
                              @Valid Post post,
                              @Context HttpServletRequest request,
                              @Context UriInfo uriInfo) {
    Forum forum = forumService.getForum(siteId.get(), forumId.get());

    if (forum == null) {
      return ErrorResponseBuilder.buildErrorResponse(1234,
          String.format("No forum found for site id: %s with forum id: %s", siteId.get(),
              forumId.get()), null, Response.Status.NOT_FOUND);
    }

    Optional<ForumThread> createdThread = threadService.createThread(siteId.get(),
        forumId.get(), post);

    if (createdThread != null) {
      forum.getThreads().add(createdThread.get());

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
