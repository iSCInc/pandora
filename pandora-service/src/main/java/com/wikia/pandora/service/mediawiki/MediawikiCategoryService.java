package com.wikia.pandora.service.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.domain.builder.CategoryBuilder;
import com.wikia.pandora.gateway.mediawiki.MediawikiGateway;

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
