package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.ExtLinksOption;

public class MWApiExtLinks extends MWApiQueryDecorator implements ExtLinksOption {

  public MWApiExtLinks(MWApiQuery parent) {
    super(parent);
  }
}
