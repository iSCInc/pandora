package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.TemplatesOption;

public class MWApiTemplates extends MWApiQueryDecorator implements TemplatesOption {

  public MWApiTemplates(MWApiQueryBase parent) {
    super(parent);
  }
}
