package com.wikia.pandora.api.service;


import com.wikia.pandora.domain.Comment;

import java.util.List;

public interface CommentService {

  Comment getComment(String wikia, Long commentId);

  List<Comment> getCommentResponses(String wikia, Long commentId);
}
