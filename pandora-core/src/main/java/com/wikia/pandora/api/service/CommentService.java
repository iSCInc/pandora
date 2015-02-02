package com.wikia.pandora.api.service;


import com.wikia.pandora.core.domain.Comment;

import java.util.Optional;

public interface CommentService {

  public Optional<Comment> getCommentsForArticle(String wikia, String title);

}
