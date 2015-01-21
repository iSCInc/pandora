package com.wikia.mobileconfig.core;

import java.util.Collections;

public class NullMobileConfiguration extends MobileConfiguration {

  public NullMobileConfiguration() {
    this.setModules(Collections.emptyList());
  }

}
