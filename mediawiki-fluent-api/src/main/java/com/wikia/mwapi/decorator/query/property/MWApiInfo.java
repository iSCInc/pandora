package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.InfoOption;

public class MWApiInfo extends MWApiQueryDecorator implements InfoOption {

  public MWApiInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
