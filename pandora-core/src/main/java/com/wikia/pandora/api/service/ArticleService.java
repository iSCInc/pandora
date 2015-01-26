package com.wikia.pandora.api.service;


import com.wikia.pandora.core.domains.Article;
import com.wikia.pandora.core.domains.Comment;

import java.util.List;

public interface ArticleService {

  public Article getArticleByTitle(String wikia, String title);

  List<Comment> getArticleComments(String wikia, String title);

  Comment getComment(String wikia, String title, Long commentId);
}
