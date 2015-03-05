package com.wikia.mobileconfig.integration;

import com.google.inject.Module;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.MobileConfigModule;

public class IntegrationTestableMobileConfigApplication extends MobileConfigApplication {

  @Override
  public Module getModule() {
    return new MobileConfigModule("prod");
  }
}
