package com.wikia.pandora.service;

import com.google.common.base.Optional;

import com.wikia.pandora.core.Article;
import com.wikia.pandora.core.Comment;

import java.util.List;

public interface ArticleService {

  public Article getArticleByTitle(String wikia, String title);

  List<Comment> getArticleComments(String wikia, String title);

  Comment getComment(String wikia, String title, Long commentId);
}
