package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.ContributorsOption;

public class MWApiContributors extends MWApiQueryDecorator implements ContributorsOption {

  public MWApiContributors(MWApiQuery parent) {
    super(parent);
  }
}
