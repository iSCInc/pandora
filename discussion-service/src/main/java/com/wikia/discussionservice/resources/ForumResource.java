package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Preconditions;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.Forums;
import com.wikia.discussionservice.services.ForumService;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import java.util.Optional;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ForumResource {

  @NonNull
  @Named("representationFactory")
  final private RepresentationFactory representationFactory;

  @NonNull
  @Named("forumService")
  final private ForumService forumService;

  /**
   * Only to satisfy the compiler, DI will ensure that
   * ForumService and RepresentationFactory are not null
   */
  private ForumResource() {
    this.forumService = null;
    this.representationFactory = null;
  }

  @GET
  @Path("/forums")
  @Timed
  public Representation getForums(@QueryParam("limit") @DefaultValue("10") IntParam limit,
                                  @QueryParam("offset") @DefaultValue("1") IntParam offset) {

    Preconditions.checkArgument(offset.get() >= 1,
        "Offset was %s but expected 1 or greater", offset.get());

    Preconditions.checkArgument(limit.get() >= 1,
        "Limit was %s but expected 1 or greater", limit.get());


    Optional<Forums> forums = forumService.getForums(offset.get(), limit.get());

    Representation representation = forums.map(this::getForumsRepresentation)
        .orElseThrow(IllegalArgumentException::new);

    return representation;
  }

  public Representation getForumsRepresentation(@NotNull Forums forums) {
    int total = forums.getTotal();
    int offset = forums.getOffset();
    int limit = forums.getLimit();

    Representation representation =
        representationFactory.newRepresentation(
            String.format("/forums?offset=%s&count=%s", offset, limit))
            .withLink("first", String.format("/forums?offset=%s&count=%s", 1, limit), "first",
                "link to first set of forums", "en-us", null)
            .withLink("last", String.format("/forums?offset=%s&count=%s", total / limit,
                limit), "last", "link to last set of forums", "en-us", null)
            .withProperty("total", total)
            .withProperty("offset", offset)
            .withProperty("limit", limit);

    if (offset > 1) {
      representation.withLink("prev", String.format("/forums?offset=%s&count=%s", offset - 1,
          limit), "previous", "previous page of forums", "en-us", null);
    }

    if (offset < total / limit - 1) {
      representation.withLink("next", String.format("/forums?offset=%s&count=%s", offset + 1,
          limit), "next", "next page of forums", "en-us", null);
    }

    if (!forums.getForums().isEmpty()) {
      for (Forum forum : forums.getForums()) {
        representation.withRepresentation("forum", getForumRepresentation(forum));
      }
    }

    return representation;
  }

  public Representation getForumRepresentation(Forum forum) {
    Representation representation =
        representationFactory.newRepresentation(String.format("/forum/%s", forum.getId()))
            .withProperty("name", forum.getName())
            .withProperty("hasChildren", forum.getChildren() != null && !forum.getChildren().isEmpty());

    return representation;
  }

}
