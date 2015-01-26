package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.domains.Article;
import com.wikia.pandora.core.domains.ArticleWithContent;
import com.wikia.pandora.core.domains.Comment;
import com.wikia.pandora.core.util.UriBuilder;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

// "{wikia}/articles/{title}" -> getArticle article from {wikia} with {title}
// "{wikia}/articles/{title}/comments" -> getArticleCommentList from {wikia} with {title}
// "{wikia}/articles/{title}/comments/{id}" -> getArticleComment from {wikia} with {id}


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
  @Path("/{title}")
  @Timed
  public Object getArticle(@PathParam("wikia") String wikia,
                           @PathParam("title") String title)
      throws java.io.IOException {
    String uri = UriBuilder.getSelfUri(wikia, title);
    Representation representation = representationFactory.newRepresentation(uri);

    Article article = this.articleService.getArticleByTitle(wikia, title);
    representation.withBean(article);
    return representation;
  }

  @GET
  @Path("withContent/{title}")
  @Timed
  public Object getArticleWithContent(@PathParam("wikia") String wikia,
                                      @PathParam("title") String title) {
    String uri = UriBuilder.getSelfUri(wikia, title);
    Representation representation = representationFactory.newRepresentation(uri);
    ArticleWithContent article = this.articleService.getArticleWithContentByTitle(wikia, title);
    representation.withBean(article);
    return representation;
  }


  @GET
  @Path("/{title}/comments")
  @Timed
  public Object getArticleCommentList(@PathParam("wikia") String wikia,
                                      @PathParam("title") String title) {
    String uri = UriBuilder.getSelfUri(wikia, title);

    Representation representation = representationFactory.newRepresentation(uri);

    List<Comment> articleComments = this.articleService.getArticleComments(wikia, title);
    for (Comment articleComment : articleComments) {
      representation.withBean(articleComment);
    }
    return representation;
  }


  @GET
  @Path("/{title}/comments/{commentId}")
  @Timed
  public Object getArticleComment(@PathParam("wikia") String wikia,
                                  @PathParam("title") String title,
                                  @PathParam("commentId") Long commentId) {
    String uri = UriBuilder.getSelfUri(wikia, title, commentId);
    Representation representation = representationFactory.newRepresentation(uri);

    Comment comment = this.articleService.getComment(wikia, title, commentId);
    representation.withBean(comment);
    return representation;
  }
}
