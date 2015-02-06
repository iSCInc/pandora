package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.CategoryInfoOption;

public class MWApiCategoryInfo extends MWApiQueryDecorator implements CategoryInfoOption {

  public MWApiCategoryInfo(MWApiQueryBase mwApiQuery) {
    super(mwApiQuery);
  }
}
