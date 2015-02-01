package com.wikia.mobileconfig.core;

import java.util.List;

public class MobileConfiguration {

  private List<Object> modules;

  public MobileConfiguration(List<Object> modules) {
    this.modules = modules;
  }

  public List<Object> getModules() {
    return modules;
  }
}
