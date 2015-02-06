package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.GlobalUsageOption;

public class MWApiGlobalUsage extends MWApiQueryDecorator implements GlobalUsageOption {

  public MWApiGlobalUsage(MWApiQueryBase parent) {
    super(parent);
  }
}
