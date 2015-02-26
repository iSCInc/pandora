package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import lombok.NonNull;

import javax.inject.Inject;

public class HALPostRepresentationMapper implements PostRepresentationMapper {

  @NonNull
  final private RepresentationFactory representationFactory;

  @Inject
  public HALPostRepresentationMapper(RepresentationFactory representationFactory) {
    this.representationFactory = representationFactory;
  }

  @Override
  public Representation buildRepresentation(int siteId, Post post) {
    return buildRepresentation(siteId, post, ResponseGroup.SMALL);
  }

  @Override
  public Representation buildRepresentation(int siteId, Post post, ResponseGroup group) {
    Representation representation =
        representationFactory.newRepresentation(
            String.format("/%s/post/%s", siteId, post.getId()))
            .withProperty("body", post.getBody())
            .withBean(post.getPoster());

    return representation;
  }
}
