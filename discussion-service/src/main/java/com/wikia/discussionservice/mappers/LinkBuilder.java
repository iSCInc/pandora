package com.wikia.discussionservice.mappers;

import lombok.NoArgsConstructor;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

@NoArgsConstructor
public class LinkBuilder {
  
  public Link buildLink(UriInfo uriInfo, String relString, Class resourceClass, String methodName,
                         Object... entities) {
    return Link.fromUriBuilder(uriInfo.getBaseUriBuilder().path(resourceClass, methodName))
        .rel(relString)
        .build(entities);
  }
}
