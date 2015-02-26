package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.mappers.ThreadRepresentationMapper;
import com.wikia.discussionservice.services.ThreadService;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import java.util.Optional;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class ThreadResource {

  @NonNull
  final private ThreadService threadService;

  @NonNull
  final private ThreadRepresentationMapper threadMapper;

  @Inject
  public ThreadResource(ThreadService threadService, ThreadRepresentationMapper threadMapper) {
    this.threadService = threadService;
    this.threadMapper = threadMapper;
  }

  @GET
  @Path("/{siteId}/thread/{threadId}")
  @Timed
  public Representation getForums(@NotNull @PathParam("siteId") IntParam siteId,
                                  @NotNull @PathParam("threadId") IntParam threadId,
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

    Optional<ForumThread> forumThread = threadService.getThread(siteId.get(), threadId.get(),
        offset.get(), limit.get());

    Representation representation = forumThread.map(
        root -> threadMapper.buildRepresentation(siteId.get(), forumThread.get(), responseGroup))
        .orElseThrow(IllegalArgumentException::new);

    return representation;
  }
}
