package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.FileUsageOption;

public class MWApiFileUsage extends MWApiQueryDecorator implements FileUsageOption {

  public MWApiFileUsage(MWApiQueryBase parent) {
    super(parent);
  }
}
