package com.wikia.pandora.service.mediawikiapi;

import com.wikia.pandora.core.Article;
import com.wikia.pandora.core.Comment;
import com.wikia.pandora.service.ArticleService;

import java.util.List;

public class MediawikiArticleService implements ArticleService {

  @Override
  public Article getArticleByTitle(String wikia, String title) {
    return null;
  }

  @Override
  public List<Comment> getArticleComments(String wikia, String title) {
    return null;
  }

  @Override
  public Comment getComment(String wikia, String title, Long commentId) {
    return null;
  }
}
