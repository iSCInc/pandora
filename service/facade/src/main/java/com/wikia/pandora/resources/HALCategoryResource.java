package com.wikia.pandora.resources;

import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.core.util.RepresentationHelper;
import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.Category;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;

@Path("{wikia}/category")
@Produces(RepresentationFactory.HAL_JSON)
public class HALCategoryResource {

  public static final int DEFAULT_LIMIT = 10;
  private final CategoryService categoryService;
  private final RepresentationFactory representationFactory;


  public HALCategoryResource(CategoryService categoryService,
                             RepresentationFactory representationFactory) {
    this.categoryService = categoryService;
    this.representationFactory = representationFactory;
  }

  @GET
  @Path("/")
  @Timed
  public Object getCategoryList(@PathParam("wikia") String wikia,
                                @DefaultValue("10") @QueryParam("limit") int limit,
                                @DefaultValue("") @QueryParam("offset") String offset,
                                @DefaultValue("") @QueryParam("previous") String previous) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HALCategoryResource.class);
    addLimitAndOffset(uriBuilder, limit, offset);

    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia));
    List<Category> categoryList = categoryService.getAllCategories(wikia, limit, offset);
    for (Category category : categoryList) {
      RepresentationHelper
          .withLinkAndTitle(representation,
                            "category",
                            getCategoryLink(wikia, category.getTitle()),
                            category.getTitle());
    }

    return representation;
  }

  @GET
  @Path("/{category}")
  @Timed
  public Object getCategory(@PathParam("wikia") String wikia,
                            @PathParam("category") String categoryName) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HALCategoryResource.class).path("{category}");
    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, categoryName));
    Category category = categoryService.getCategory(wikia, categoryName);

    representation.withBean(category);

    representation.withLink("articles", uriBuilder.path("articles").build(wikia, categoryName));

    return representation;
  }


  @GET
  @Timed
  @Path("/{category}/articles")
  public Object getCategoryArticles(@PathParam("wikia") String wikia,
                                    @PathParam("category") String categoryName,
                                    @DefaultValue("10") @QueryParam("limit") int limit,
                                    @DefaultValue("") @QueryParam("offset") String offset,
                                    @DefaultValue("") @QueryParam("previous") String previous) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HALCategoryResource.class)
        .path("{category}").path("articles");

    addLimitAndOffset(uriBuilder, limit, offset);

    if (previous == null || Objects.equals(previous, "")) {
      uriBuilder.queryParam("previous", previous);
    }

    Representation
        representation =
        representationFactory
            .newRepresentation(uriBuilder.build(wikia, categoryName));
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

  private void addLimitAndOffset(UriBuilder uriBuilder, int limit, String offset) {
    if (limit == DEFAULT_LIMIT || limit == 0) {
      uriBuilder.queryParam("limit", limit);
    }
    if (offset == null || Objects.equals(offset, "")) {
      uriBuilder.queryParam("offset", offset);
    }
  }


  private String getArticleLink(String wikia, String title) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/articles/{title}")
        .build(wikia, title).getPath();
  }

  private String getCategoryLink(String wikia, String title) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/category/{title}")
        .build(wikia, title).getPath();
  }
}
