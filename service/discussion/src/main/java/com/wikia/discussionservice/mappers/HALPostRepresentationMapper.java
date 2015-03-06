package com.wikia.discussionservice.mappers;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.enums.ResponseGroup;
import com.wikia.discussionservice.resources.PostResource;
import com.wikia.discussionservice.resources.ThreadResource;
import lombok.NonNull;

import javax.inject.Inject;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

public class HALPostRepresentationMapper implements PostRepresentationMapper {

  @NonNull
  final private RepresentationFactory representationFactory;

  @Inject
  public HALPostRepresentationMapper(RepresentationFactory representationFactory) {
    this.representationFactory = representationFactory;
  }

  @Override
  public Representation buildRepresentation(int siteId, Post post, UriInfo uriInfo) {
    return buildRepresentation(siteId, post, uriInfo, ResponseGroup.SMALL);
  }

  @Override
  public Representation buildRepresentation(int siteId, Post post, UriInfo uriInfo,
                                            ResponseGroup group) {

    Link linkToSelf = new LinkBuilder().buildLink(uriInfo, "self", PostResource.class, "getPost",
        siteId, post.getId());
    Link linkToThread = new LinkBuilder().buildLink(uriInfo, "up", ThreadResource.class,
        "getThread", siteId, post.getId());

    Representation representation =
        representationFactory.newRepresentation()
            .withLink("self", linkToSelf.getUri())
            .withLink("up", linkToThread.getUri())
            .withBean(post);

    return representation;
  }
}
