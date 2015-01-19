package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface ArticleResource {

  @GET
  @Path("/{title}")
  @Timed
  Object getArticle(@PathParam("wikia") String wikia,
                    @PathParam("title") String title)
      throws java.io.IOException;

  @GET
  @Path("/{title}/comments")
  @Timed
  Object getArticleCommentList(@PathParam("wikia") String wikia,
                               @PathParam("title") String title);

  @GET
  @Path("/{title}/comments/{commentId}")
  @Timed
  Object getArticleComment(@PathParam("wikia") String wikia,
                           @PathParam("title") String title,
                           @PathParam("commentId") Long commentId);
}
