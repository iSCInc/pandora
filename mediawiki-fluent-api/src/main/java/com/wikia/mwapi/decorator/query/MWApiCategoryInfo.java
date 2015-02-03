package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.CategoryInfoOption;

public class MWApiCategoryInfo extends MWApiQueryDecorator implements CategoryInfoOption {

  public MWApiCategoryInfo(MWApiQuery mwApiQuery) {
    super(mwApiQuery);
  }
}
