package com.wikia.pandora.service;

import com.google.common.base.Optional;

import com.wikia.pandora.core.Comment;


public interface CommentService {

  public Optional<Comment> getCommentsForArticle(String wikia, String title);

}
