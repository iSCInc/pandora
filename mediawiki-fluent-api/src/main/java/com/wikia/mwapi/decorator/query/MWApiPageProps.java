package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.PagePropsOption;

public class MWApiPageProps extends MWApiQueryDecorator implements PagePropsOption {

  public MWApiPageProps(MWApiQuery parent) {
    super(parent);
  }
}
