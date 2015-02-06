package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.ExtLinksOption;

public class MWApiExtLinks extends MWApiQueryDecorator implements ExtLinksOption {

  public MWApiExtLinks(MWApiQueryBase parent) {
    super(parent);
  }
}
