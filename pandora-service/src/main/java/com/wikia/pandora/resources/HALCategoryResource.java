package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.core.util.RepresentationHelper;
import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.core.util.UriBuilder;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, categoryName);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, categoryName));
    Category category = categoryService.getCategory(wikia, categoryName);
    representation.withBean(category);
    return representation;
  }

  @GET
  @Timed
  @Path("/{category}/articles")
  public Object getCategoryArticles(@PathParam("wikia") String wikia,
                                    @PathParam("category") String categoryName,
                                    @DefaultValue("10") @QueryParam("limit") int limit,
                                    @QueryParam("offset") int offset) {
    javax.ws.rs.core.UriBuilder
        uri = javax.ws.rs.core.UriBuilder.fromPath("/{category}/articles");

    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, categoryName, limit, offset));
    List<Article>
        articleList =
        categoryService.getCategoryArticles(wikia, categoryName, limit, offset);

    for (Article article : articleList) {
      RepresentationHelper.withLinkAndTitle(representation,
                                            "article",
                                            getArticleLink(wikia, article.getTitle()),
                                            article.getTitle());
    }

    return representation;
  }

  private String getArticleLink(String wikia, String title) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/articles/{title}")
        .build(wikia, title).getPath();
  }
}
