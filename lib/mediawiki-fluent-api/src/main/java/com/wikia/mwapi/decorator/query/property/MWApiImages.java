package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.ImagesOption;

public class MWApiImages extends MWApiQueryDecorator implements ImagesOption {

  public MWApiImages(MWApiQueryBase parent) {
    super(parent);
  }
}
