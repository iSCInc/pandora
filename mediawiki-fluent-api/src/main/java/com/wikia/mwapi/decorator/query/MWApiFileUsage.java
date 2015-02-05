package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.FileUsageOption;

public class MWApiFileUsage extends MWApiQueryDecorator implements FileUsageOption {

  public MWApiFileUsage(MWApiQueryBase parent) {
    super(parent);
  }
}
