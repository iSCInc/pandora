package com.wikia.discussionservice.mappers;

import com.google.inject.BindingAnnotation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.*;
import com.wikia.discussionservice.enums.ResponseGroup;
import lombok.NonNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

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
  public Representation buildRepresentation(int siteId, ForumRoot forumRoot) {
    return buildRepresentation(siteId, forumRoot, ResponseGroup.SMALL);
  }

  @Override
  public Representation buildRepresentation(int siteId, @NotNull ForumRoot forumRoot,
                                            ResponseGroup responseGroup) {
    int total = forumRoot.getTotal();
    int offset = forumRoot.getOffset();
    int limit = forumRoot.getLimit();

    Representation representation =
        representationFactory.newRepresentation(
            String.format("/%s/forums/?offset=%s&count=%s", siteId, offset, limit))
            .withLink("first", String.format("/%s/forums?offset=%s&count=%s", siteId,
                1, limit), "first", "link to first set of forums", "en-us", null)
            .withLink("last", String.format("/%s/forums?offset=%s&count=%s", siteId,
                total / limit, limit), "last", "link to last set of forums", "en-us", null)
            .withProperty("total", total)
            .withProperty("offset", offset)
            .withProperty("limit", limit);

    if (offset > 1) {
      representation.withLink("prev", String.format("/%s/forums/?offset=%s&count=%s",
              siteId, offset - 1, limit), "previous",
          "previous page of forums", "en-us", null);
    }

    if (offset < total / limit - 1) {
      representation.withLink("next", String.format("/%s/forums?offset=%s&count=%s",
              siteId, offset + 1, limit), "next",
          "next page of forums", "en-us", null);
    }

    if (!forumRoot.getForums().isEmpty()) {
      for (Forum forum : forumRoot.getForums()) {
        representation.withRepresentation("forum", buildRepresentation(
            siteId, forum, responseGroup));
      }
    }

    return representation;
  }

  @Override
  public Representation buildRepresentation(int siteId, Forum forum) {
    return buildRepresentation(siteId, forum, ResponseGroup.SMALL);
  }

  @Override
  public Representation buildRepresentation(int siteId, Forum forum,
                                            ResponseGroup responseGroup) {

    Representation representation =
        representationFactory.newRepresentation(
            String.format("/%s/forum/%s", siteId, forum.getId()))
            .withProperty("name", forum.getName())
            .withProperty("hasChildren", forum.hasChildren())
            .withProperty("hasThreads", forum.hasThreads());

    for (Forum childForum : forum.getChildren()) {
      representation.withRepresentation("forum", buildRepresentation(
          siteId, childForum));
    }

    for (ForumThread thread : forum.getThreads()) {
      representation.withRepresentation("thread", threadRepresentationMapper.buildRepresentation(
          siteId, thread));
    }

    return representation;
  }
}
