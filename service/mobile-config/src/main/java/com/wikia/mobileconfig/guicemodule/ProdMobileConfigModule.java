package com.wikia.mobileconfig.guicemodule;

import com.wikia.mobileconfig.service.application.AppsDeployerListContainer;
import com.wikia.mobileconfig.service.application.AppsListService;


public class ProdMobileConfigModule extends MobileConfigModuleBase {

  @Override
  protected void configure() {
    super.configure();
    bind(AppsListService.class).to(AppsDeployerListContainer.class);
  }
}
