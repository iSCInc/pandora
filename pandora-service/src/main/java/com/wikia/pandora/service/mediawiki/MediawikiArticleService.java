package com.wikia.pandora.service.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.domain.User;
import com.wikia.mwapi.domain.Image;
import com.wikia.mwapi.domain.Page;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.ArticleWithContent;
import com.wikia.pandora.domain.ArticleWithDescription;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.domain.Comment;
import com.wikia.pandora.domain.Media;
import com.wikia.pandora.domain.Revision;
import com.wikia.pandora.domain.builder.PojoBuilderFactory;
import com.wikia.pandora.gateway.mediawiki.MediawikiGateway;


import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class MediawikiArticleService extends MediawikiService implements ArticleService {

  public MediawikiArticleService(MediawikiGateway gateway) {
    super(gateway);
  }

  @Override
  public Article getArticleByTitle(String wikia, String title) {
    ApiResponse apiResponse = this.getGateway().getArticleByTitle(wikia, title);

    return PojoBuilderFactory.getArticleBuilder()
        .withId(apiResponse.getQuery().getFirstPage().getPageId())
        .withTitle(apiResponse.getQuery().getFirstPage().getTitle())
        .withNs(apiResponse.getQuery().getFirstPage().getNs())
        .build();
  }

  @Override
  public ArticleWithDescription getArticleWithDescriptionByTitle(String wikia, String title) {
    throw new NotImplementedException("");
  }

  @Override
  public ArticleWithContent getArticleWithContentByTitle(String wikia, String title) {
    ApiResponse apiResponse = this.getGateway().getArticleWithContentByTitle(wikia, title);

    return PojoBuilderFactory.getArticleWithContentBuilder()
        .withId(apiResponse.getQuery().getFirstPage().getPageId())
        .withTitle(apiResponse.getQuery().getFirstPage().getTitle())
        .withNs(apiResponse.getQuery().getFirstPage().getNs())
        .withContent(apiResponse.getQuery().getFirstPage().getFirstRevision().getContent())
        .withDescription("TODO LATER")//TODO
        .build();
  }

  @Override
  public List<Comment> getArticleComments(String wikia, String title) {
    throw new NotImplementedException("");
  }

  @Override
  public Comment getComment(String wikia, String title, Long commentId) {
    throw new NotImplementedException("");
  }

  @Override
  public List<Article> getArticlesFromWikia(String wikia) {
    ApiResponse apiResponse = this.getGateway().getArticlesFromWikia(wikia);
    List<Article> articleList = new ArrayList<Article>();
    for (Page page : apiResponse.getQuery().getAllPages()) {
      Article article =
          PojoBuilderFactory.getArticleBuilder()
              .withId(page.getPageId())
              .withTitle(page.getTitle())
              .withNs(page.getNs()).build();
      articleList.add(article);
    }
    return articleList;
  }

  @Override
  public List<Category> getArticleCategories(String wikia, String title) {
    ApiResponse apiResponse = this.getGateway().getArticleCategories(wikia, title);
    List<Category> categoryList = new ArrayList<>();
    if (apiResponse.getQuery().getFirstPage() != null
        && apiResponse.getQuery().getFirstPage().getCategories() != null) {
      for (com.wikia.mwapi.domain.Category mwCategory : apiResponse.getQuery().getFirstPage()
          .getCategories()) {
        Category category = PojoBuilderFactory.getCategoryBuilder()
            .withNs(mwCategory.getNs())
            .withTitle(mwCategory.getTitle())
            .build();
        categoryList.add(category);
      }
    }
    return categoryList;
  }

  @Override
  public List<Media> getArticleMedia(String wikia, String title) {
    ApiResponse apiResponse = this.getGateway().getArticleImages(wikia, title);
    List<Media> mediaList = new ArrayList<>();
    if (apiResponse.getQuery().getFirstPage() != null
        && apiResponse.getQuery().getFirstPage().getImages() != null) {
      for (Image image : apiResponse.getQuery().getFirstPage().getImages()) {
        Media media = PojoBuilderFactory.getMediaBuilder()
            .withNs(image.getNs())
            .withTitle(image.getTitle())
            .build();
        mediaList.add(media);
      }
    }
    return mediaList;
  }

  @Override
  public List<Revision> getArticleRevisions(String wikia, String title) {
    ApiResponse apiResponse = this.getGateway().getArticleRevisions(wikia, title);
    List<Revision> revisions = new ArrayList<>();
    if (apiResponse.getQuery().getFirstPage() != null
        && apiResponse.getQuery().getFirstPage().getRevisions() != null) {
      for (com.wikia.mwapi.domain.Revision mwRevision : apiResponse.getQuery().getFirstPage()
          .getRevisions()) {
        Revision revision = PojoBuilderFactory.getRevisionBuilder()
            .withComment(mwRevision.getComment())
            .withContent(mwRevision.getContent())
            .withParentId(mwRevision.getParentId())
            .withRevId(mwRevision.getRevId())
            .withTimestamp(mwRevision.getTimestamp())
            .withUser(mwRevision.getUser())
            .build();
        revisions.add(revision);
      }
    }
    return revisions;
  }

  @Override
  public List<com.wikia.pandora.domain.User> getArticleContributors(String wikia, String title) {
    ApiResponse apiResponse = this.getGateway().getArticleContributors(wikia, title);
    List<com.wikia.pandora.domain.User> users = new ArrayList<>();
    if (apiResponse.getQuery().getFirstPage() != null
        && apiResponse.getQuery().getFirstPage().getContributors() != null) {
      for (User mwUser : apiResponse.getQuery().getFirstPage()
          .getContributors()) {
        com.wikia.pandora.domain.User user = PojoBuilderFactory.getUserBuilder()
            .withId(mwUser.getUserId())
            .withName(mwUser.getName())
            .build();
        users.add(user);
      }
    }

    return users;
  }

}
