package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.InfoOption;

public class MWApiInfo extends MWApiQueryDecorator implements InfoOption {

  public MWApiInfo(MWApiQuery parent) {
    super(parent);
  }
}
