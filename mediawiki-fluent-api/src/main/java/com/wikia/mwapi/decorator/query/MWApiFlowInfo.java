package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.fluent.query.FlowInfoOption;

public class MWApiFlowInfo extends MWApiQueryDecorator implements FlowInfoOption {

  public MWApiFlowInfo(MWApiQuery parent) {
    super(parent);
  }
}
