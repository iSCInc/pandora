package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.LinksOption;

public class MWApiLinks extends MWApiQueryDecorator implements LinksOption {

  public MWApiLinks(MWApiQueryBase parent) {
    super(parent);
  }
}
