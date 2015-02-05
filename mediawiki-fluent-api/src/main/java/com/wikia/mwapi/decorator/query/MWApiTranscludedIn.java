package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.TranscludedInOption;

public class MWApiTranscludedIn extends MWApiQueryDecorator implements TranscludedInOption {

  public MWApiTranscludedIn(MWApiQueryBase parent) {
    super(parent);
  }
}
