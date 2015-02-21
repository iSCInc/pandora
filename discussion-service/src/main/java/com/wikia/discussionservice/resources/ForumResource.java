package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.Forums;
import com.wikia.discussionservice.services.ForumService;
import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
@RequiredArgsConstructor
public class ForumResource {

  @Autowired
  @NonNull
  final private RepresentationFactory representationFactory;

  @Autowired
  @NonNull
  final private ForumService forumService;

  /**
   * Only to statisfy the compiler, DI will ensure that
   * ForumService and RepresentationFactory are not null
   */
  private ForumResource() {
    this.forumService = null;
    this.representationFactory = null;
  }

  @GET
  @Path("/forums")
  @Timed
  public Representation getForums(@QueryParam("count") @DefaultValue("10") IntParam count,
                                  @QueryParam("start") @DefaultValue("1") IntParam start) {

    Forums forums = forumService.getForums(start.get(), count.get());

    int total = forums.getTotal();
    Representation representation = representationFactory.newRepresentation("/forums")
        .withLink("self", String.format("/forums?start=%s", forums.getStart()))
        .withLink("first", String.format("/forums?start=%s", 1))
        .withLink("last", String.format("/forums?start=%s", total / count.get()))
        .withProperty("total", total)
        .withProperty("start", start.get())
        .withProperty("count", count.get());

    if (start.get() > 0) {
      representation.withLink("previous", String.format("/forums?start=%s", start.get()));
    }

    if (start.get() < total/count.get() - 1) {
      representation.withLink("next", String.format("/forums?start=%s", start.get()));
    }

    if (!forums.getForums().isEmpty()) {
      for(Forum forum: forums.getForums()) {
        representation.withRepresentation("forum", getForumRepresentation(forum));
      }
    }

    return representation;
  }

  public Representation getForumRepresentation(Forum forum) {
    Representation representation = representationFactory.newRepresentation(
        String.format("/forum/%s", forum.getId()));

    representation.withProperty("name", forum.getName());
    representation.withProperty("hasChildren",
        forum.getChildren() != null && !forum.getChildren().isEmpty());

    return representation;
  }

}
