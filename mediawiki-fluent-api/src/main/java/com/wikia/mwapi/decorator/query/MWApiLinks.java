package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.LinksOption;

public class MWApiLinks extends MWApiQueryDecorator implements LinksOption {

  public MWApiLinks(MWApiQuery parent) {
    super(parent);
  }
}
