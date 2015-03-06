package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.resources.ForumResource;
import com.wikia.discussionservice.resources.ThreadResource;
import lombok.NonNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

public class HALForumRepresentationMapper implements ForumRepresentationMapper {

  @NonNull
  final private RepresentationFactory representationFactory;

  @NonNull
  final private ThreadRepresentationMapper threadMapper;

  @Inject
  public HALForumRepresentationMapper(RepresentationFactory representationFactory,
                                      ThreadRepresentationMapper threadMapper) {
    this.representationFactory = representationFactory;
    this.threadMapper = threadMapper;
  }

  @Override
  public Representation buildRepresentation(int siteId, ForumRoot forumRoot, UriInfo uriInfo) {
    return buildRepresentation(siteId, forumRoot, uriInfo, ResponseGroup.SMALL);
  }

  @Override
  public Representation buildRepresentation(int siteId, @NotNull ForumRoot forumRoot,
                                            UriInfo uriInfo, ResponseGroup responseGroup) {

    Link linkToSelf = new LinkBuilder().buildLink(uriInfo, "self", ForumResource.class,
        "getForums", siteId);
    Link createFormLink = new LinkBuilder().buildLink(uriInfo, "create-form", ForumResource.class,
        "createForum", siteId);

    Representation representation = representationFactory.newRepresentation()
        .withLink("self", linkToSelf.getUri())
        .withLink("create-form", createFormLink.getUri().getPath(), "createForum", "createForum",
            "us-en", null)
        .withProperty("total", forumRoot.getForums().size());

    if (!forumRoot.getForums().isEmpty()) {
      for (Forum forum : forumRoot.getForums()) {
        representation.withRepresentation("forum", buildRepresentation(
            siteId, forum, uriInfo, responseGroup));
      }
    }

    return representation;
  }

  @Override
  public Representation buildRepresentation(int siteId, Forum forum, UriInfo uriInfo) {
    return buildRepresentation(siteId, forum, uriInfo, ResponseGroup.SMALL);
  }

  @Override
  public Representation buildRepresentation(int siteId, Forum forum, UriInfo uriInfo,
                                            ResponseGroup responseGroup) {

    Link linkToSelf = new LinkBuilder().buildLink(uriInfo, "self", ForumResource.class, "getForum",
        siteId, forum.getId());

    Link linkToStartThread = new LinkBuilder().buildLink(uriInfo, "self", ThreadResource.class,
        "startThread", siteId, forum.getId());

    Representation representation =
        representationFactory.newRepresentation()
            .withLink("self", linkToSelf.getUri())
            .withLink("create-form", linkToStartThread.getUri().getPath(), "startThread",
                "startThread", "us-en", null)
            .withProperty("name", forum.getName());

    if (forum.hasChildren()) {
      for (Forum childForum : forum.getChildren()) {
        representation.withRepresentation("forum", buildRepresentation(
            siteId, childForum, uriInfo));
      }
    } else {
      representation.withProperty("forum", new ArrayList());
    }

    if (forum.hasThreads()) {
      for (ForumThread thread : forum.getThreads()) {
        representation.withRepresentation("thread", threadMapper.buildRepresentation(
            siteId, thread, uriInfo));
      }
    } else {
      representation.withProperty("thread", new ArrayList());
    }

    return representation;
  }
}
