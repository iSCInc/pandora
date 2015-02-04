package com.wikia.pandora.api.service;


import com.wikia.pandora.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

  Comment getComment(String wikia, Long commentId);

  List<Comment> getCommentResponses(String wikia, Long commentId);
}
