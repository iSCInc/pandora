package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.core.util.RepresentationHelper;
import com.wikia.pandora.core.util.UriBuilder;
import com.wikia.pandora.domain.Comment;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("{wikia}/comments")
@Produces(RepresentationFactory.HAL_JSON)
public class HalCommentResource {

  private final CommentService commentService;
  private final RepresentationFactory representationFactory;


  public HalCommentResource(CommentService commentService,
                            RepresentationFactory representationFactory) {

    this.commentService = commentService;
    this.representationFactory = representationFactory;
  }

  @GET
  @Timed
  @Path("/{commentId}")
  public Object getComment(@PathParam("wikia") String wikia,
                           @PathParam("commentId") Long commentId) {

    javax.ws.rs.core.UriBuilder uriBuilder = UriBuilder.getSelfUriBuilder(wikia, commentId);
    Representation
        representation =
        this.representationFactory
            .newRepresentation(uriBuilder.build(wikia, commentId));

    Comment comment = commentService.getComment(wikia, commentId);
    representation.withBean(comment);

    String userName = comment.getUserName();
    RepresentationHelper.withLinkAndTitle(representation, "user",
                                          getLinkToUser(wikia, userName),
                                          userName);

    String articleName = comment.getArticleName();
    RepresentationHelper.withLinkAndTitle(representation, "article",
                                          getLinkToArticle(wikia, articleName),
                                          articleName);

    representation.withLink("responses", uriBuilder.path("responses").build(wikia, commentId));

    return representation;
  }

  @GET
  @Timed
  @Path("/{commentId}/responses")
  public Object getCommentResponses(@PathParam("wikia") String wikia,
                                    @PathParam("commentId") Long commentId) {
    javax.ws.rs.core.UriBuilder uriBuilder = UriBuilder.getSelfUriBuilder(wikia, commentId);
    Representation
        representation =
        this.representationFactory
            .newRepresentation(uriBuilder.build(wikia, commentId));

    List<Comment> commentList = commentService.getCommentResponses(wikia, commentId);
    for (Comment comment : commentList) {
      RepresentationHelper
          .withLinkAndTitle(representation,
                            "response",
                            getLinkToComment(wikia, comment.getId()),
                            comment.getUserName());
    }
    return representation;
  }

  private String getLinkToComment(String wikia, Long id) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/comments/{id}")
        .build(wikia, id).getPath();
  }


  private String getLinkToArticle(String wikia, String articleName) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/articles/{title}")
        .build(wikia, articleName).getPath();
  }

  private String getLinkToUser(String wikia, String userName) {
    return javax.ws.rs.core.UriBuilder
        .fromPath("{wikia}/users/{userName}")
        .build(wikia, userName).getPath();
  }
}
