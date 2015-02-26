package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;

public interface PostRepresentationMapper {

  public Representation buildRepresentation(int siteId, Post post);

  public Representation buildRepresentation(int siteId, Post post, ResponseGroup group);
}
