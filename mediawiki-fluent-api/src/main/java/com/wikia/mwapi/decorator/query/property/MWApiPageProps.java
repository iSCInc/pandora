package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.PagePropsOption;

public class MWApiPageProps extends MWApiQueryDecorator implements PagePropsOption {

  public MWApiPageProps(MWApiQueryBase parent) {
    super(parent);
  }
}
