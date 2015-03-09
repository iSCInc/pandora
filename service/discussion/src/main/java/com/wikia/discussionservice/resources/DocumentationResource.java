package com.wikia.discussionservice.resources;

import com.wikia.discussionservice.configuration.DiscussionServiceConfiguration;
import com.wikia.discussionservice.views.ResourceView;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Multimap;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.views.View;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class DocumentationResource {

  @NonNull
  final private RepresentationFactory representationFactory;

  @NonNull
  final private DiscussionServiceConfiguration configuration;

  @Inject
  public DocumentationResource(DiscussionServiceConfiguration configuration,
                               RepresentationFactory representationFactory) {
    this.representationFactory = representationFactory;
    this.configuration = configuration;
  }

  @GET
  @Path("/{siteId}/rels/{rel}")
  @Timed
  public View getDocumentation(@NotNull @PathParam("siteId") IntParam siteId,
                               @NotNull @PathParam("rel") String relation) {

    Map<String, String> relMap = new HashMap<>();
    relMap.put("forums", String.format("/%s/rels/forums", siteId.get()));
    relMap.put("threads", String.format("/%s/rels/threads", siteId.get()));
    relMap.put("posts", String.format("/%s/rels/posts", siteId.get()));

    Class resourceClass = null;
    if (relation.equalsIgnoreCase("forums")) {
      resourceClass = ForumResource.class;
    } else if (relation.equalsIgnoreCase("threads")) {
      resourceClass = ThreadResource.class;
    } else if (relation.equalsIgnoreCase("posts")) {
      // TODO: Implement the documentation for Posts
      // resourceClass = PostResource.class;
    }

    if (resourceClass != null) {
      return ResourceView.createResourceView(relMap, resourceClass);
    }

    throw new IllegalArgumentException(String.format("Unknown relation: %s", relation));
  }
}
