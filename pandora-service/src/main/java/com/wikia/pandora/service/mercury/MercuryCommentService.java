package com.wikia.pandora.service.mercury;

import com.wikia.pandora.api.service.CommentService;
import com.wikia.pandora.domain.Comment;
import com.wikia.pandora.domain.builder.CommentBuilder;
import com.wikia.pandora.gateway.mercury.MercuryGateway;

import java.util.ArrayList;
import java.util.List;

public class MercuryCommentService extends MercuryService implements CommentService {

  public MercuryCommentService(MercuryGateway mercuryGateway) {
    super(mercuryGateway);
  }
  
  ///Intential duplicate code of mock. Do we want to create package for mocks? or this is ok?

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
                       .withUserName(String.format("mock user %d", i))
                       .withId(commentId + i)
                       .build());
    }
    return comments;
  }
}
