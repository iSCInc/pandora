package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.PostRepresentationMapper;
import com.wikia.discussionservice.services.PostService;
import com.wikia.discussionservice.services.ThreadService;
import com.wikia.discussionservice.utils.ErrorResponseBuilder;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class PostResource {

  @NonNull
  final private PostService postService;

  @NonNull
  final private ThreadService threadService;

  @NonNull
  final private PostRepresentationMapper postMapper;

  @Inject
  public PostResource(PostService postService,
                      ThreadService threadService,
                      PostRepresentationMapper postMapper) {
    this.postService = postService;
    this.threadService = threadService;
    this.postMapper = postMapper;
  }

  @GET
  @Path("/{siteId}/posts/{postId}")
  @Timed
  public Response getPost(@NotNull @PathParam("siteId") IntParam siteId,
                          @NotNull @PathParam("postId") IntParam postId,
                          @QueryParam("responseGroup") @DefaultValue("small")
                          String requestedResponseGroup,
                          @Context UriInfo uriInfo) {

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<Post> post = postService.getPost(siteId.get(), postId.get());

    if (post != null) {
      Representation representation = postMapper.buildRepresentation(
          siteId.get(), post.get(), uriInfo, responseGroup);

      return Response.ok(representation)
          .build();
    }


    return ErrorResponseBuilder.buildErrorResponse(10101,
        String.format("No Post found for site id: %s with forum id: %s", siteId.get(),
            postId.get()),
        null, Response.Status.NOT_FOUND);
  }

  @POST
  @Path("/{siteId}/posts")
  @Timed
  public Response createPost(@NotNull @PathParam("siteId") IntParam siteId,
                             Post post,
                             @Context UriInfo uriInfo) {

    Optional<ForumThread> thread = threadService.getForumThread(siteId.get(), post.getThreadId());

    if (!thread.isPresent()) {
      return ErrorResponseBuilder.buildErrorResponse(1234,
          String.format("No thread found for site id: %s with thread id: %s", siteId.get(),
              post.getThreadId()), null, Response.Status.NOT_FOUND);
    }

    Optional<Post> createdPost = postService.createPost(siteId.get(), post.getThreadId(), post);

    if (createdPost.isPresent()) {
      thread.get().getPosts().add(post);

      Representation representation = postMapper.buildRepresentation(siteId.get(),
          createdPost.get(), uriInfo);

      return Response.status(Response.Status.CREATED)
          .entity(representation)
          .build();
    }

    return ErrorResponseBuilder.buildErrorResponse(10101, String.format(
            "Unable to create post for site id: %s on thread id: %s", siteId.get(), 
            post.getThreadId()), null, Response.Status.BAD_REQUEST);
  }
}
