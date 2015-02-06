package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.PageTermsOption;

public class MWApiPageTerms extends MWApiQueryDecorator implements PageTermsOption {

  public MWApiPageTerms(MWApiQueryBase parent) {
    super(parent);
  }
}
