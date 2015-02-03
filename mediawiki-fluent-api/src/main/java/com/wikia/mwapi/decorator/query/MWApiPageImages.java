package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.PageImagesOption;

public class MWApiPageImages extends MWApiQueryDecorator implements PageImagesOption {

  public MWApiPageImages(MWApiQuery parent) {
    super(parent);
  }
}
