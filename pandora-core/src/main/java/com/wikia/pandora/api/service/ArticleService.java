package com.wikia.pandora.api.service;


import com.wikia.pandora.core.domain.Article;
import com.wikia.pandora.core.domain.ArticleWithContent;
import com.wikia.pandora.core.domain.ArticleWithDescription;
import com.wikia.pandora.core.domain.Category;
import com.wikia.pandora.core.domain.Comment;
import com.wikia.pandora.core.domain.Media;
import com.wikia.pandora.core.domain.Revision;
import com.wikia.pandora.core.domain.User;

import java.util.List;

public interface ArticleService {

  Article getArticleByTitle(String wikia, String title);

  ArticleWithDescription getArticleWithDescriptionByTitle(String wikia, String title);

  ArticleWithContent getArticleWithContentByTitle(String wikia, String title);

  List<Comment> getArticleComments(String wikia, String title);

  Comment getComment(String wikia, String title, Long commentId);

  List<Article> getArticlesFromWikia(String wikia);

  List<Category> getArticleCategories(String wikia, String title);

  List<Media> getArticleMedia(String wikia, String title);


  List<Revision> getArticleRevisions(String wikia, String title);

  List<User> getArticleUsers(String wikia, String title);
}
