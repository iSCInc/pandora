package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.resources.ThreadResource;
import lombok.NonNull;

import javax.inject.Inject;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

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
  
  public Representation buildRepresentation(int siteId, ForumThread thread, UriInfo uriInfo) {
    return buildRepresentation(siteId, thread, uriInfo, 0, 10, ResponseGroup.SMALL);
  }

  public Representation buildRepresentation(int siteId, ForumThread thread, UriInfo uriInfo, 
                                            int offset, int limit) {
    return buildRepresentation(siteId, thread, uriInfo, offset, limit, ResponseGroup.SMALL);
  }


  public Representation buildRepresentation(int siteId, ForumThread thread, 
                                            UriInfo uriInfo, ResponseGroup responseGroup) {
    return buildRepresentation(siteId, thread, uriInfo, 1, 10, responseGroup);
  }

  public Representation buildRepresentation(int siteId, ForumThread thread,
                                            UriInfo uriInfo, int offset, int limit, 
                                            ResponseGroup responseGroup) {
    Link linkToSelf = new LinkBuilder().buildLink(uriInfo, "self", ThreadResource.class,
        "getForumThread", siteId, thread.getId());
    
    Representation representation =
        representationFactory.newRepresentation()
            .withLink("self", linkToSelf.getUri())
            .withProperty("title", thread.getTitle())
            .withRepresentation("lastPost",
                postRepresentationMapper.buildRepresentation(siteId, thread.getLastPost(), 
                    uriInfo));

    if (responseGroup == ResponseGroup.FULL) {
      for(Post post: thread.getPosts()) {
        representation.withRepresentation("post", 
            postRepresentationMapper.buildRepresentation(siteId, post, uriInfo));
      }
    }

    return representation;
  }

}
