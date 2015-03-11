package com.wikia.mobileconfig.guicemodule;

import com.wikia.mobileconfig.service.application.AppsListService;
import com.wikia.mobileconfig.service.application.CephAppsListService;

public class QaMobileConfigModule extends MobileConfigModuleBase {

  @Override
  protected void configure() {
    super.configure();
    bind(AppsListService.class).to(CephAppsListService.class);
  }
}
