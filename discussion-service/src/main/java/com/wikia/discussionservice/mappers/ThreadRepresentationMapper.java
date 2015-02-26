package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.enums.ResponseGroup;

public interface ThreadRepresentationMapper {

  public Representation buildRepresentation(int siteId, ForumThread thread);

  public Representation buildRepresentation(int siteId, ForumThread thread, ResponseGroup group);
}
