package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.GlobalUsageOption;

public class MWApiGlobalUsage extends MWApiQueryDecorator implements GlobalUsageOption {

  public MWApiGlobalUsage(MWApiQueryBase parent) {
    super(parent);
  }
}
