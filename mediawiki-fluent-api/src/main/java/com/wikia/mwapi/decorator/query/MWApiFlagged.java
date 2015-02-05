package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.FlaggedOption;

public class MWApiFlagged extends MWApiQueryDecorator implements FlaggedOption {

  public MWApiFlagged(MWApiQueryBase parent) {
    super(parent);
  }
}
