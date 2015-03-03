package com.wikia.mwapi.decorator.query.property;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.fluent.query.FlowInfoOption;

public class MWApiFlowInfo extends MWApiQueryDecorator implements FlowInfoOption {

  public MWApiFlowInfo(MWApiQueryBase parent) {
    super(parent);
  }
}
