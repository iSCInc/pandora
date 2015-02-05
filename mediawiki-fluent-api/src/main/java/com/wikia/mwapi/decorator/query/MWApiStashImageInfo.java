package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.StashImageInfoOption;

public class MWApiStashImageInfo extends MWApiQueryDecorator implements StashImageInfoOption {

  public MWApiStashImageInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
