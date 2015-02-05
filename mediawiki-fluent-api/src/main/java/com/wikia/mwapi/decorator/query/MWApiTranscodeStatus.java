package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.TranscodeStatusOption;

public class MWApiTranscodeStatus extends MWApiQueryDecorator implements TranscodeStatusOption {

  public MWApiTranscodeStatus(MWApiQueryBase parent) {
    super(parent);
  }
}
