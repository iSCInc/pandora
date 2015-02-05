package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.RedirectsOption;

public class MWApiRedirects extends MWApiQueryDecorator implements RedirectsOption {

  public MWApiRedirects(MWApiQueryBase parent) {
    super(parent);
  }
}
