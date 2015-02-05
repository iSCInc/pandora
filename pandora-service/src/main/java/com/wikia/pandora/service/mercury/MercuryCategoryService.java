package com.wikia.pandora.service.mercury;

import com.wikia.pandora.api.service.CategoryService;
import com.wikia.pandora.domain.Category;
import com.wikia.pandora.gateway.mercury.MercuryGateway;

public class MercuryCategoryService extends MercuryService implements CategoryService {

  public MercuryCategoryService(MercuryGateway mercuryGateway) {
    super(mercuryGateway);
  }

  @Override
  public Category getCategory(String wikia, String category) {
    return new Category(0, 0, "");
  }

}
