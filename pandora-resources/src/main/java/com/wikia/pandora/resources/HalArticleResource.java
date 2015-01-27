package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.domain.Article;
import com.wikia.pandora.core.domain.ArticleWithContent;
import com.wikia.pandora.core.util.UriBuilder;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

// "{wikia}/articles" -> getArticles get all articles (currently first 10) from {wikia}
// "{wikia}/articles/{title}" -> getArticle article from {wikia} with {title}
// "{wikia}/articles/{title}/comments" -> getArticleComments from {wikia} with {title}
// "{wikia}/articles/{title}/users" -> getArticleComments from {wikia} with {title}
// "{wikia}/articles/{title}/comments" -> getArticleComments from {wikia} with {title}
// "{wikia}/articles/{title}/comments" -> getArticleComments from {wikia} with {title}
// "{wikia}/articles/{title}/comments" -> getArticleComments from {wikia} with {title}



@Path("{wikia}/articles")
@Produces(RepresentationFactory.HAL_JSON)
public class HalArticleResource {


  private final ArticleService articleService;
  private final RepresentationFactory representationFactory;

  public HalArticleResource(ArticleService articleService,
                            RepresentationFactory representationFactory) {

    this.articleService = articleService;
    this.representationFactory = representationFactory;
  }

  @GET
  @Path("/")
  @Timed
  public Object getArticles(@PathParam("wikia") String wikia) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia);
    Representation representation = representationFactory.newRepresentation(uri.build(wikia));
    List<Article> articleList = articleService.getArticlesFromWikia(wikia);
    for (Article article : articleList) {
      representation.withLink(
          "articles",
          getLinkToArticle(wikia, article.getTitle())
          , null
          , article.getTitle()
          , null
          , null);
    }

    return representation;
  }

  private String getLinkToArticle(String wikia, String title) {
    return
        javax.ws.rs.core.UriBuilder
            .fromResource(HalArticleResource.class)
            .path("/{title}")
            .build(wikia, title)
            .getPath();


  }

  @GET
  @Path("/{title}")
  @Timed
  public Object getArticle(@PathParam("wikia") String wikia,
                           @PathParam("title") String title) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, title);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, title));

    Article article = this.articleService.getArticleByTitle(wikia, title);
    representation.withBean(article);
    representation.withLink("categories", uri.clone().path("categories").build(wikia, title));
    representation.withLink("users", uri.clone().path("users").build(wikia, title));
    representation.withLink("media", uri.clone().path("media").build(wikia, title));
    representation.withLink("comments", uri.clone().path("comments").build(wikia, title));
    representation.withLink("revisions", uri.clone().path("revisions").build(wikia, title));

    return representation;
  }

  @GET
  @Path("/{title}/categories")
  @Timed
  public Object getArticleCategories(@PathParam("wikia") String wikia,
                                     @PathParam("title") String title) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, title);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, title));

    return representation;
  }

  @GET
  @Path("/{title}/comments")
  @Timed
  public Object getArticleComments(@PathParam("wikia") String wikia,
                                   @PathParam("title") String title) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, title);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, title));

    return representation;
  }

  @GET
  @Path("/{title}/media")
  @Timed
  public Object getArticleMedia(@PathParam("wikia") String wikia,
                                @PathParam("title") String title) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, title);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, title));

    return representation;
  }

  @GET
  @Path("/{title}/revisions")
  @Timed
  public Object getArticleRevisions(@PathParam("wikia") String wikia,
                                    @PathParam("title") String title) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, title);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, title));

    return representation;
  }

  @GET
  @Path("/{title}/users")
  @Timed
  public Object getArticleUsers(@PathParam("wikia") String wikia,
                                @PathParam("title") String title) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, title);
    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, title));

    return representation;
  }


  @GET
  @Path("withContent/{title}")
  @Timed
  public Object getArticleWithContent(@PathParam("wikia") String wikia,
                                      @PathParam("title") String title) {
    javax.ws.rs.core.UriBuilder uri = UriBuilder.getSelfUriBuilder(wikia, title);

    Representation
        representation =
        representationFactory.newRepresentation(uri.build(wikia, title));
    ArticleWithContent article = this.articleService.getArticleWithContentByTitle(wikia, title);
    representation.withBean(article);

    return representation;
  }
}
