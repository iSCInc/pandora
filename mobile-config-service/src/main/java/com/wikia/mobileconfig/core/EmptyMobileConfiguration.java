package com.wikia.mobileconfig.core;

import java.util.Collections;

public class EmptyMobileConfiguration extends MobileConfiguration {

  public EmptyMobileConfiguration() {
    this.setModules(Collections.emptyList());
  }

}
