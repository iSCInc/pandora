package com.wikia.pandora.api.service;


import com.wikia.pandora.domain.Comment;

import java.util.Optional;

public interface CommentService {

  public Optional<Comment> getCommentsForArticle(String wikia, String title);

}
