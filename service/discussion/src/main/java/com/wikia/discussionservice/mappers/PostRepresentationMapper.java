package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;

import javax.ws.rs.core.UriInfo;

public interface PostRepresentationMapper {

  public Representation buildRepresentation(int siteId, Post post, UriInfo uriInfo);

  public Representation buildRepresentation(int siteId, Post post, UriInfo uriInfo,
                                            ResponseGroup group);
}
