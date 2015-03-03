package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.ContributorsOption;

public class MWApiContributors extends MWApiQueryDecorator implements ContributorsOption {

  public MWApiContributors(MWApiQueryBase parent) {
    super(parent);
  }
}
