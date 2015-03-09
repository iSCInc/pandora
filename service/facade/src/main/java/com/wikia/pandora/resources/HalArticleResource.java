package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.util.RepresentationHelper;
import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.domain.Comment;
import com.wikia.pandora.domain.Media;
import com.wikia.pandora.domain.Revision;
import com.wikia.pandora.domain.User;

import java.net.URI;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriBuilder;

// "{wikia}/articles" -> getArticles get all articles (currently first 10) from {wikia}
// "{wikia}/articles/{title}" -> getArticle article from {wikia} with {title}
// "{wikia}/articles/{title}/comments" -> getArticleComments from {wikia} with {title}
// "{wikia}/articles/{title}/users" -> getArticleContributors from {wikia} with {title}
// "{wikia}/articles/{title}/categories" -> getArticleCategories from {wikia} with {title}
// "{wikia}/articles/{title}/media" -> getArticleMedia from {wikia} with {title}
// "{wikia}/articles/{title}/revisions" -> getArticleRevisions from {wikia} with {title}


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
    URI uri = UriBuilder.fromResource(HalArticleResource.class).build(wikia);
    Representation representation = representationFactory.newRepresentation(uri);
    List<Article> articleList = articleService.getArticlesFromWikia(wikia);
    for (Article article : articleList) {

      RepresentationHelper.withLinkAndTitle(
          representation,
          "articles",
          getLinkToArticle(wikia, article.getTitle()),
          article.getTitle());
    }

    return representation;
  }

  @GET
  @Path("/{title}")
  @Timed
  public Object getArticle(@PathParam("wikia") String wikia,
                           @PathParam("title") String title) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HalArticleResource.class).path("{title}");

    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, title));

    Article article = this.articleService.getArticleByTitle(wikia, title);
    representation.withBean(article);
    representation
        .withLink("categories", uriBuilder.clone().path("categories").build(wikia, title));
    representation.withLink("users", uriBuilder.clone().path("users").build(wikia, title));
    representation.withLink("media", uriBuilder.clone().path("media").build(wikia, title));
    representation.withLink("comments", uriBuilder.clone().path("comments").build(wikia, title));
    representation.withLink("revisions", uriBuilder.clone().path("revisions").build(wikia, title));

    return representation;
  }

  @GET
  @Path("/{title}/categories")
  @Timed
  public Object getArticleCategories(@PathParam("wikia") String wikia,
                                     @PathParam("title") String title) {
    UriBuilder
        uriBuilder =
        UriBuilder.fromResource(HalArticleResource.class)
            .path("{title}").path("categories");
    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, title));
    representation.withLink("article", uriBuilder.clone()
        .replacePath("{wikia}/articles/{title}")
        .build(wikia, title));

    List<Category> categories = articleService.getArticleCategories(wikia, title);
    for (Category category : categories) {
      RepresentationHelper
          .withLinkAndTitle(representation,
                            "category",
                            getLinkToCategory(wikia, category.getTitle()),
                            category.getTitle());
    }

    return representation;
  }

  @GET
  @Path("/{title}/comments")
  @Timed
  public Object getArticleComments(@PathParam("wikia") String wikia,
                                   @PathParam("title") String title) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HalArticleResource.class)
        .path("{title}").path("comments");
    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, title));
    representation.withLink("article", uriBuilder.clone()
        .replacePath("{wikia}/articles/{title}").build(wikia, title));
    List<Comment> comments = articleService.getArticleComments(wikia, title);
    for (Comment comment : comments) {
      RepresentationHelper
          .withLinkAndTitle(
              representation,
              "comment",
              getLinkToComment(wikia, comment.getId()),
              comment.getText());
    }

    return representation;
  }

  @GET
  @Path("/{title}/media")
  @Timed
  public Object getArticleMedia(@PathParam("wikia") String wikia,
                                @PathParam("title") String title) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HalArticleResource.class)
        .path("{title}").path("media");
    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, title));
    List<Media> mediaList = articleService.getArticleMedia(wikia, title);
    for (Media media : mediaList) {
      RepresentationHelper
          .withLinkAndTitle(
              representation,
              "media",
              getLinkToMedia(wikia, media.getTitle()),
              media.getTitle()
          );
    }

    return representation;
  }

  @GET
  @Path("/{title}/revisions")
  @Timed
  public Object getArticleRevisions(@PathParam("wikia") String wikia,
                                    @PathParam("title") String title) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HalArticleResource.class)
        .path("{title}").path("revisions");
    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, title));
    List<Revision> revisions = articleService.getArticleRevisions(wikia, title);
    for (Revision revision : revisions) {
      RepresentationHelper
          .withLinkAndTitle(
              representation,
              "revision",
              getLinkToRevision(wikia, revision.getRevId()),
              String.format("%s: %s", revision.getUser(), revision.getComment())
          );
    }

    return representation;
  }

  @GET
  @Path("/{title}/users")
  @Timed
  public Object getArticleUsers(@PathParam("wikia") String wikia,
                                @PathParam("title") String title) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HalArticleResource.class)
        .path("{title}").path("users");
    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, title));
    List<User> users = articleService.getArticleContributors(wikia, title);
    for (User user : users) {
      RepresentationHelper
          .withLinkAndTitle(
              representation, "user",
              getLinkToUser(wikia, user.getName()),
              user.getName());
    }
    return representation;
  }

  private String getLinkToArticle(String wikia, String title) {
    return
        UriBuilder
            .fromResource(HalArticleResource.class)
            .path("/{title}")
            .build(wikia, title)
            .getPath();


  }

  private String getLinkToCategory(String wikia, String title) {
    return UriBuilder
        .fromPath("{wikia}/categories/{title}")
        .build(wikia, title).getPath();
  }

  private String getLinkToComment(String wikia, Long id) {
    return UriBuilder
        .fromPath("{wikia}/comments/{id}")
        .build(wikia, id).getPath();
  }

  private String getLinkToMedia(String wikia, String title) {
    return UriBuilder
        .fromPath("{wikia}/media/{title}")
        .build(wikia, title).getPath();
  }

  private String getLinkToUser(String wikia, String name) {
    return UriBuilder
        .fromPath("{wikia}/users/{name}")
        .build(wikia, name).getPath();
  }


  private String getLinkToRevision(String wikia, int revId) {
    return UriBuilder
        .fromPath("{wikia}/revision/{revId}")
        .build(wikia, revId).getPath();
  }
}
