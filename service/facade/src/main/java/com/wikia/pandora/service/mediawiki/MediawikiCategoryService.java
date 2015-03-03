package com.wikia.pandora.service.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.domain.CategoryStat;
import com.wikia.mwapi.domain.Page;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.domain.builder.ArticleBuilder;
import com.wikia.pandora.domain.builder.CategoryBuilder;
import com.wikia.pandora.gateway.mediawiki.MediawikiGateway;

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
                                           String offset) {
    List<Article> articleList = new ArrayList<>();

    ApiResponse apiResponse = getGateway().getCategoryArticles(wikia, categoryName, limit, offset);
    if (apiResponse.getQuery() != null) {
      for (Page page : apiResponse.getQuery().getCategorymembers()) {
        Article
            article =
            ArticleBuilder.anArticle()
                .withTitle(page.getTitle())
                .withId(page.getPageId())
                .withNs(page.getNs())
                .build();
        articleList.add(article);
      }
    }

    return articleList;
  }

  @Override
  public List<Category> getAllCategories(String wikia, int limit, String offset) {
    List<Category> categoryList = new ArrayList<>();

    ApiResponse apiResponse = getGateway().getCategoriesFromWikia(wikia, limit, offset);
    if (apiResponse.getQuery() != null && apiResponse.getQuery().getAllCategories() != null) {
      for (CategoryStat categoryStat : apiResponse.getQuery().getAllCategories()) {
        Category category = CategoryBuilder.aCategory()
            .withTitle(categoryStat.getCategoryName())
            .build();
        categoryList.add(category);
      }
    }
    return categoryList;
  }
}
