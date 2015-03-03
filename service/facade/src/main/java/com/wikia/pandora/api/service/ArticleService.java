package com.wikia.pandora.api.service;


import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.ArticleWithContent;
import com.wikia.pandora.domain.ArticleWithDescription;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.domain.Comment;
import com.wikia.pandora.domain.Media;
import com.wikia.pandora.domain.Revision;
import com.wikia.pandora.domain.User;

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

  List<User> getArticleContributors(String wikia, String title);
}
