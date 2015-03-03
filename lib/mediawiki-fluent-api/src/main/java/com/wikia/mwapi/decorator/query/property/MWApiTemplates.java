package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.TemplatesOption;

public class MWApiTemplates extends MWApiQueryDecorator implements TemplatesOption {

  public MWApiTemplates(MWApiQueryBase parent) {
    super(parent);
  }
}
