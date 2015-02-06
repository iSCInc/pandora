package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.StashImageInfoOption;

public class MWApiStashImageInfo extends MWApiQueryDecorator implements StashImageInfoOption {

  public MWApiStashImageInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
