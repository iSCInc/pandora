package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.enums.ResponseGroup;

/**
 * RepresentationMapper is an interface for abstracting
 * mapping of objects to their representation.
 *  
 */
public interface ForumRepresentationMapper {
  
  public Representation buildRepresentation(int siteId, ForumRoot forumRoot);
  
  public Representation buildRepresentation(int siteId, ForumRoot forumRoot, 
                                            ResponseGroup responseGroup);

  public Representation buildRepresentation(int siteId, Forum forum);

  public Representation buildRepresentation(int siteId, Forum forum, ResponseGroup responseGroup);
}
