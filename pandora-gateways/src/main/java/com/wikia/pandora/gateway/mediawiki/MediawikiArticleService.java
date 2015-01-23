package com.wikia.pandora.gateway.mediawiki;

import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.domains.Article;
import com.wikia.pandora.core.domains.Comment;

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
