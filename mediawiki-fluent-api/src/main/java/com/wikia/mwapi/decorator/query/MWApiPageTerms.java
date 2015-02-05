package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.PageTermsOption;

public class MWApiPageTerms extends MWApiQueryDecorator implements PageTermsOption {

  public MWApiPageTerms(MWApiQueryBase parent) {
    super(parent);
  }
}
