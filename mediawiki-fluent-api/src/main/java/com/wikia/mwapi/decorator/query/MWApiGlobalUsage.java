package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.GlobalUsageOption;

public class MWApiGlobalUsage extends MWApiQueryDecorator implements GlobalUsageOption {

  public MWApiGlobalUsage(MWApiQuery parent) {
    super(parent);
  }
}
