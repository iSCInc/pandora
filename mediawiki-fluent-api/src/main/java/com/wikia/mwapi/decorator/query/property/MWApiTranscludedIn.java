package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.TranscludedInOption;

public class MWApiTranscludedIn extends MWApiQueryDecorator implements TranscludedInOption {

  public MWApiTranscludedIn(MWApiQueryBase parent) {
    super(parent);
  }
}
