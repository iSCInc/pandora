package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.TranscodeStatusOption;

public class MWApiTranscodeStatus extends MWApiQueryDecorator implements TranscodeStatusOption {

  public MWApiTranscodeStatus(MWApiQueryBase parent) {
    super(parent);
  }
}
