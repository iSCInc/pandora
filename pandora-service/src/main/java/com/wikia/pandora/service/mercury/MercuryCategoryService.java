package com.wikia.pandora.service.mercury;

import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.domain.builder.ArticleBuilder;
import com.wikia.pandora.domain.builder.CategoryBuilder;
import com.wikia.pandora.gateway.mercury.MercuryGateway;

import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class MercuryCategoryService extends MercuryService implements CategoryService {

  public MercuryCategoryService(MercuryGateway gateway) {
    super(gateway);
  }

  @Override
  public Category getCategory(String wikia, String categoryName) {
    return CategoryBuilder.aCategory().withTitle(String.format("%s mock", categoryName)).withNs(14)
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
