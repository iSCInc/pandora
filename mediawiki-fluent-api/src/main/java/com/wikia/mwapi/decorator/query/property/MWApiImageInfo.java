package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.ImageInfoOption;

public class MWApiImageInfo extends MWApiQueryDecorator implements ImageInfoOption {

  public MWApiImageInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
