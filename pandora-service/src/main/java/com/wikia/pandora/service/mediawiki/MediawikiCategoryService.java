package com.wikia.pandora.service.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.domain.builder.ArticleBuilder;
import com.wikia.pandora.domain.builder.CategoryBuilder;
import com.wikia.pandora.gateway.mediawiki.MediawikiGateway;

import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class MediawikiCategoryService extends MediawikiService implements CategoryService {

  public MediawikiCategoryService(MediawikiGateway gateway) {
    super(gateway);
  }


  @Override
  public Category getCategory(String wikia, String category) {
    ApiResponse apiResponse = getGateway().getCategoryByName(wikia, category);
    return CategoryBuilder.aCategory()
        .withTitle(apiResponse.getQuery().getFirstPage().getTitle())
        .withNs(apiResponse.getQuery().getFirstPage().getNs())
        .withPageId(apiResponse.getQuery().getFirstPage().getPageId())
        .build();
  }

  @Override
  public List<Article> getCategoryArticles(String wikia, String categoryName, int limit,
                                           int offset) {
    List<Article> mockList = new ArrayList<>();
    for (int i = 0; i < limit; i++) {
      int id = offset + i;
      Article
          article =
          ArticleBuilder.anArticle()
              .withId(id)
              .withTitle("mock article" + id)
              .build();
      mockList.add(article);
    }
    return mockList;
  }
}
