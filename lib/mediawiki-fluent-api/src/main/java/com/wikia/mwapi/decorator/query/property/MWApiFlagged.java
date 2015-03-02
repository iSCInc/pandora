package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.FlaggedOption;

public class MWApiFlagged extends MWApiQueryDecorator implements FlaggedOption {

  public MWApiFlagged(MWApiQueryBase parent) {
    super(parent);
  }
}
