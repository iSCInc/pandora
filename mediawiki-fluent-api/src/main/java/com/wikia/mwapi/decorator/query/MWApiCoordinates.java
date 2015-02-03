package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.CoordinatesOption;

public class MWApiCoordinates extends MWApiQueryDecorator implements CoordinatesOption {

  public MWApiCoordinates(MWApiQuery parent) {
    super(parent);
  }
}
