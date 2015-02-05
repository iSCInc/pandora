package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.DuplicateFilesOption;

public class MWApiDuplicateFiles extends MWApiQueryDecorator implements DuplicateFilesOption {

  public MWApiDuplicateFiles(MWApiQueryBase parent) {
    super(parent);
  }
}
