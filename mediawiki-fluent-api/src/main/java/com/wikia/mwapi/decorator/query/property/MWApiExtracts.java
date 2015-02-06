package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.ExtractsOption;

public class MWApiExtracts extends MWApiQueryDecorator implements ExtractsOption {

  public MWApiExtracts(MWApiQueryBase parent) {
    super(parent);
  }
}
