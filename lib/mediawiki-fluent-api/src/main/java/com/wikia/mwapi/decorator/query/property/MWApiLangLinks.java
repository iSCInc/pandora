package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.LangLinksOption;

public class MWApiLangLinks extends MWApiQueryDecorator implements LangLinksOption {

  public MWApiLangLinks(MWApiQueryBase parent) {
    super(parent);
  }
}
