package com.wikia.pandora.gateway.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.domain.Page;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.domain.Article;
import com.wikia.pandora.core.domain.ArticleWithContent;
import com.wikia.pandora.core.domain.ArticleWithDescription;
import com.wikia.pandora.core.domain.Comment;
import com.wikia.pandora.core.domain.builder.PojoBuilderFactory;

import org.apache.commons.lang.NotImplementedException;

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
    throw new NotImplementedException();
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
    throw new NotImplementedException();
  }

  @Override
  public Comment getComment(String wikia, String title, Long commentId) {
    throw new NotImplementedException();
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
}
