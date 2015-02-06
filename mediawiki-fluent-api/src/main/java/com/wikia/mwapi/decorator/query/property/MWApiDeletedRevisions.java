package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.DeletedRevisionsOption;

public class MWApiDeletedRevisions extends MWApiQueryDecorator implements DeletedRevisionsOption {

  
  public MWApiDeletedRevisions(MWApiQueryBase parent) {
    super(parent);
  }
}
