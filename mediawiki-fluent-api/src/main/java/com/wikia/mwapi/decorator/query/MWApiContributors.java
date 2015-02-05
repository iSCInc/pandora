package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.ContributorsOption;

public class MWApiContributors extends MWApiQueryDecorator implements ContributorsOption {

  public MWApiContributors(MWApiQueryBase parent) {
    super(parent);
  }
}
