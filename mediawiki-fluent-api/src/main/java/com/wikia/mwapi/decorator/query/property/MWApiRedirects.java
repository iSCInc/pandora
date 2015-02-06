package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.RedirectsOption;

public class MWApiRedirects extends MWApiQueryDecorator implements RedirectsOption {

  public MWApiRedirects(MWApiQueryBase parent) {
    super(parent);
  }
}
