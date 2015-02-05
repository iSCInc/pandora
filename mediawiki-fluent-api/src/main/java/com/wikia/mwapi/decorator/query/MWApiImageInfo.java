package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.ImageInfoOption;

public class MWApiImageInfo extends MWApiQueryDecorator implements ImageInfoOption {

  public MWApiImageInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
