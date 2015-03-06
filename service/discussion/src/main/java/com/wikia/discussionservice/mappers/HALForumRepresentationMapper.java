package com.wikia.discussionservice.mappers;

import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.resources.ForumResource;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import lombok.NonNull;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

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

    Representation representation = representationFactory.newRepresentation()
        .withLink("self", linkToSelf.getUri())
        .withLink("doc:forums", linkToSelf.getUri().getPath(), "forums", null, null, null)
        .withProperty("total", forumRoot.getForums().size())
        .withNamespace("doc", String.format("/%s/rels/{rel}", siteId));

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
            .withLink("self", linkToSelf.getUri().getPath(), "doc:thread", null, null, null)
            .withNamespace("doc", String.format("/%s/rels/{rel}", siteId))
            .withProperty("name", forum.getName());

    if (forum.hasChildren()) {
      for (Forum childForum : forum.getChildren()) {
        representation.withRepresentation("doc:forum", buildRepresentation(
            siteId, childForum, uriInfo));
      }
    } else {
      representation.withProperty("forum", new ArrayList());
    }

    if (forum.hasThreads()) {
      for (ForumThread thread : forum.getThreads()) {
        representation.withRepresentation("doc:thread", threadMapper.buildRepresentation(
            siteId, thread, uriInfo));
      }
    } else {
      representation.withProperty("thread", new ArrayList());
    }

    return representation;
  }
}
