package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.PageImagesOption;

public class MWApiPageImages extends MWApiQueryDecorator implements PageImagesOption {

  public MWApiPageImages(MWApiQueryBase parent) {
    super(parent);
  }
}
