package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import lombok.NonNull;

import javax.inject.Inject;

public class HALThreadRepresentationMapper implements ThreadRepresentationMapper {
  
  @NonNull
  final private RepresentationFactory representationFactory;
  
  @NonNull
  final private PostRepresentationMapper postRepresentationMapper;

  @Inject
  public HALThreadRepresentationMapper(RepresentationFactory representationFactory, 
                                       PostRepresentationMapper postRepresentationMapper) {
    this.representationFactory = representationFactory;
    this.postRepresentationMapper = postRepresentationMapper;
  }
  
  public Representation buildRepresentation(int siteId, ForumThread thread) {
    return buildRepresentation(siteId, thread, ResponseGroup.SMALL);
  }

  public Representation buildRepresentation(int siteId, ForumThread thread,
                                                ResponseGroup responseGroup) {
    Representation representation =
        representationFactory.newRepresentation(
            String.format("/%s/thread/%s", siteId, thread.getId()))
            .withProperty("title", thread.getTitle())
            .withRepresentation("lastPost", 
                postRepresentationMapper.buildRepresentation(siteId, thread.getLastPost()));

    if (responseGroup == ResponseGroup.FULL) {
      for(Post post: thread.getPosts()) {
        representation.withRepresentation("post", 
            postRepresentationMapper.buildRepresentation(siteId, post));
      }
    }

    return representation;
  }

}
