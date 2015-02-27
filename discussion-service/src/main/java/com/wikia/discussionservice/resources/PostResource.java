package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.PostRepresentationMapper;
import com.wikia.discussionservice.mappers.ThreadRepresentationMapper;
import com.wikia.discussionservice.services.PostService;
import com.wikia.discussionservice.services.ThreadService;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class PostResource {

  @NonNull
  final private PostService postService;

  @NonNull
  final private PostRepresentationMapper postMapper;

  @Inject
  public PostResource(PostService postService, PostRepresentationMapper postMapper) {
    this.postService = postService;
    this.postMapper = postMapper;
  }

  @GET
  @Path("/{siteId}/post/{postId}")
  @Timed
  public Representation getPost(@NotNull @PathParam("siteId") IntParam siteId,
                                  @NotNull @PathParam("threadId") IntParam postId,
                                  @QueryParam("responseGroup") @DefaultValue("small")
                                  String requestedResponseGroup,
                                  @Context UriInfo uriInfo) {

    ResponseGroup responseGroup = ResponseGroup.getResponseGroup(requestedResponseGroup);
    Preconditions.checkNotNull(responseGroup, "Invalid response group");

    Optional<Post> post = postService.getPost(siteId.get(), postId.get());

    Representation representation = post.map(
        root -> postMapper.buildRepresentation(
            siteId.get(), post.get(), uriInfo, responseGroup))
        .orElseThrow(IllegalArgumentException::new);

    return representation;
  }
}
