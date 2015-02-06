package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.CoordinatesOption;

public class MWApiCoordinates extends MWApiQueryDecorator implements CoordinatesOption {

  public MWApiCoordinates(MWApiQueryBase parent) {
    super(parent);
  }
}
