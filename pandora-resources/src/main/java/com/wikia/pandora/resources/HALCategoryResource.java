package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.core.domains.Category;
import com.wikia.pandora.core.util.UriBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("{wikia}/category")
@Produces(RepresentationFactory.HAL_JSON)
public class HALCategoryResource {

  private final CategoryService categoryService;
  private final RepresentationFactory representationFactory;


  public HALCategoryResource(CategoryService categoryService,
                             RepresentationFactory representationFactory) {
    this.categoryService = categoryService;
    this.representationFactory = representationFactory;
  }


  @GET
  @Path("/{category}")
  @Timed
  public Object getCategory(@PathParam("wikia") String wikia,
                            @PathParam("category") String categoryName) {
    String uri = UriBuilder.getSelfUri(wikia, categoryName);
    Representation representation = representationFactory.newRepresentation(uri);
    Category category = categoryService.getCategory(wikia, categoryName);
    representation.withBean(category);
    return representation;
  }
}
