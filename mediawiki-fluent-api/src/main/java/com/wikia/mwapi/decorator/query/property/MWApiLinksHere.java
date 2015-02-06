package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.LinksHereOption;

public class MWApiLinksHere extends MWApiQueryDecorator implements LinksHereOption {

  public MWApiLinksHere(MWApiQueryBase parent) {
    super(parent);
  }
}
