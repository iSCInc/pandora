package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.LinksHereOption;

public class MWApiLinksHere extends MWApiQueryDecorator implements LinksHereOption {

  public MWApiLinksHere(MWApiQueryBase parent) {
    super(parent);
  }
}
