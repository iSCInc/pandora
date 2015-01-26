package com.wikia.pandora.gateway.mediawiki;

import com.wikia.mwapi.ApiResponse;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.domains.Article;
import com.wikia.pandora.core.domains.ArticleWithContent;
import com.wikia.pandora.core.domains.ArticleWithDescription;
import com.wikia.pandora.core.domains.Comment;
import com.wikia.pandora.core.domains.builder.PojoBuilderFactory;

import org.apache.commons.lang.NotImplementedException;

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
}
