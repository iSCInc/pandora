package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.enums.ResponseGroup;

import javax.ws.rs.core.UriInfo;

public interface ThreadRepresentationMapper {

  public Representation buildRepresentation(int siteId, ForumThread thread, UriInfo uriInfo);

  public Representation buildRepresentation(int siteId, ForumThread thread, UriInfo uriInfo,
                                            ResponseGroup group);
}
