package com.wikia.pandora.gateway.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.core.domain.Category;
import com.wikia.pandora.core.domain.builder.CategoryBuilder;

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
}
