package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.VideoInfoOption;

public class MWApiVideoInfo extends MWApiQueryDecorator implements VideoInfoOption {

  public MWApiVideoInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
