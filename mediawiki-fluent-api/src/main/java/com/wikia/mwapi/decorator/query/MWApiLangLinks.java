package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.LangLinksOption;

public class MWApiLangLinks extends MWApiQueryDecorator implements LangLinksOption {

  public MWApiLangLinks(MWApiQueryBase parent) {
    super(parent);
  }
}
