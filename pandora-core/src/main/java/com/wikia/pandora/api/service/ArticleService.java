package com.wikia.pandora.api.service;


import com.wikia.pandora.core.domain.Article;
import com.wikia.pandora.core.domain.ArticleWithContent;
import com.wikia.pandora.core.domain.ArticleWithDescription;
import com.wikia.pandora.core.domain.Comment;

import java.util.List;

public interface ArticleService {

  Article getArticleByTitle(String wikia, String title);

  ArticleWithDescription getArticleWithDescriptionByTitle(String wikia, String title);

  ArticleWithContent getArticleWithContentByTitle(String wikia, String title);

  List<Comment> getArticleComments(String wikia, String title);

  Comment getComment(String wikia, String title, Long commentId);

  List<Article> getArticlesFromWikia(String wikia);
}
