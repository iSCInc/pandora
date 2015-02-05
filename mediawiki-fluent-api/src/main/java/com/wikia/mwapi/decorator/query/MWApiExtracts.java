package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.ExtractsOption;

public class MWApiExtracts extends MWApiQueryDecorator implements ExtractsOption {

  public MWApiExtracts(MWApiQueryBase parent) {
    super(parent);
  }
}
