package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.IWLinksOption;

public class MWApiIWLinks extends MWApiQueryDecorator implements IWLinksOption {

  public MWApiIWLinks(MWApiQueryBase parent) {
    super(parent);
  }
}
