package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.DuplicateFilesOption;

public class MWApiDuplicateFiles extends MWApiQueryDecorator implements DuplicateFilesOption {

  public MWApiDuplicateFiles(MWApiQueryBase parent) {
    super(parent);
  }
}
