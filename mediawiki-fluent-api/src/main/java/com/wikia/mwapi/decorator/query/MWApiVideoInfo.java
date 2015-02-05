package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.VideoInfoOption;

public class MWApiVideoInfo extends MWApiQueryDecorator implements VideoInfoOption {

  public MWApiVideoInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
