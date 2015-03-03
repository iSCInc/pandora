package com.wikia.mobileconfig.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Applications {
  private final List<HashMap<String, Object>> apps;

  public Applications(List<HashMap<String, Object>> apps) {
    this.apps = Collections.unmodifiableList(apps);
  }

  public List<HashMap<String, Object>> getApps() {
    return apps;
  }
}
