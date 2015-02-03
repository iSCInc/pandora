package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.ImagesOption;

public class MWApiImages extends MWApiQueryDecorator implements ImagesOption {

  public MWApiImages(MWApiQuery parent) {
    super(parent);
  }
}
