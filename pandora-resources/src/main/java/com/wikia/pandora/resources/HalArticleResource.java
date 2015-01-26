package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.domains.Article;
import com.wikia.pandora.core.domains.Comment;
import com.wikia.pandora.core.util.UriBuilder;


import java.util.List;

import javax.ws.rs.*;

// "{wikia}/articles/{title}" -> getArticle article from {wikia} with {title}
// "{wikia}/articles/{title}/comments" -> getArticleCommentList from {wikia} with {title}
// "{wikia}/articles/{title}/comments/{id}" -> getArticleComment from {wikia} with {id}


@Path("{wikia}/articles")
@Produces(RepresentationFactory.HAL_JSON)
public class HalArticleResource {


  private final ArticleService articleService;
  private RepresentationFactory representationFactory;

  public HalArticleResource(ArticleService articleService,
                            RepresentationFactory representationFactory) {

    this.articleService = articleService;
    this.representationFactory = representationFactory;
  }

  /**
   * GET /articles/<wikia>/<title> FIXME: Is this the right way to handle the wikia? It's not the
   * current convention, but it's simple. Do we do any domain magic? Are there any other
   * considerations?
   *
   * Comment: I suggest to use  <wikia>/articles/<title>. Also move title to method annotation
   *
   * @throws java.io.IOException, WebApplicationException
   */

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
