package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.DeletedRevisionsOption;

public class MWApiDeletedRevisions extends MWApiQueryDecorator implements DeletedRevisionsOption {

  
  public MWApiDeletedRevisions(MWApiQueryBase parent) {
    super(parent);
  }
}
