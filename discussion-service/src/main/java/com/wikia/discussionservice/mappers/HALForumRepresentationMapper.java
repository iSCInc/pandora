package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.resources.ForumResource;
import lombok.NonNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

public class HALForumRepresentationMapper implements ForumRepresentationMapper {

  @NonNull
  final private RepresentationFactory representationFactory;

  @NonNull
  final private ThreadRepresentationMapper threadRepresentationMapper;

  @Inject
  public HALForumRepresentationMapper(RepresentationFactory representationFactory,
                                      ThreadRepresentationMapper threadRepresentationMapper) {
    this.representationFactory = representationFactory;
    this.threadRepresentationMapper = threadRepresentationMapper;
  }

  @Override
  public Representation buildRepresentation(int siteId, ForumRoot forumRoot, UriInfo uriInfo) {
    return buildRepresentation(siteId, forumRoot, uriInfo, ResponseGroup.SMALL);
  }

  @Override
  public Representation buildRepresentation(int siteId, @NotNull ForumRoot forumRoot,
                                            UriInfo uriInfo, ResponseGroup responseGroup) {

    Link linkToSelf = new LinkBuilder().buildLink(uriInfo, "self", ForumResource.class, "getForums", siteId);

    Representation representation = representationFactory.newRepresentation()
        .withLink("self", linkToSelf.getUri())
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

    Representation representation =
        representationFactory.newRepresentation()
            .withLink("self", linkToSelf.getUri())
            .withProperty("name", forum.getName())
            .withProperty("hasChildren", forum.hasChildren())
            .withProperty("hasThreads", forum.hasThreads());

    for (Forum childForum : forum.getChildren()) {
      representation.withRepresentation("forum", buildRepresentation(
          siteId, childForum, uriInfo));
    }

    for (ForumThread thread : forum.getThreads()) {
      representation.withRepresentation("thread", threadRepresentationMapper.buildRepresentation(
          siteId, thread, uriInfo));
    }

    return representation;
  }
}
