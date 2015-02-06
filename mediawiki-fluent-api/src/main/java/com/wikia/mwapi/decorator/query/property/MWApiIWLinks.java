package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.IWLinksOption;

public class MWApiIWLinks extends MWApiQueryDecorator implements IWLinksOption {

  public MWApiIWLinks(MWApiQueryBase parent) {
    super(parent);
  }
}
