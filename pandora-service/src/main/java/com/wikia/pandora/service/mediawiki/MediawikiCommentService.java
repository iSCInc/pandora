package com.wikia.pandora.service.mediawiki;

import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.domain.Comment;
import com.wikia.pandora.domain.builder.CommentBuilder;
import com.wikia.pandora.gateway.mediawiki.MediawikiGateway;


import java.util.ArrayList;
import java.util.List;

public class MediawikiCommentService extends MediawikiService implements CommentService {

  public MediawikiCommentService(MediawikiGateway gateway) {
    super(gateway);
  }

  ///Intential duplicate code of mock. Do we want to create package for mocks? or this is

  @Override
  public Comment getComment(String wikia, Long commentId) {
    return CommentBuilder
        .aComment()
        .withArticleName("mock article")
        .withId(commentId)
        .withText("mock comment text")
        .withUserName("mock user")
        .withWikiaName("mock wiki")
        .build();
  }

  @Override
  public List<Comment> getCommentResponses(String wikia, Long commentId) {
    CommentBuilder builder = CommentBuilder
        .aComment()
        .withArticleName(String.format("comment to comment %d", commentId))
        .withId(commentId)
        .withText("mock comment text")
        .withUserName("mock user")
        .withWikiaName("mock wiki");
    List<Comment> comments = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      comments.add(builder
                       .but()
                       .withUserName(String.format("mock user %d", i)
                       ).withId(commentId + i)
                       .build());
    }
    return comments;
  }
}
