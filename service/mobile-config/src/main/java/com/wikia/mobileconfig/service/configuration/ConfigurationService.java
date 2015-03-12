package com.wikia.mobileconfig.service.configuration;

import com.wikia.mobileconfig.core.MobileConfiguration;

import java.io.IOException;

public interface ConfigurationService {

  public MobileConfiguration getDefault(String platform) throws IOException;

  public MobileConfiguration getConfiguration(String platform, String appTag, String uiLang, String contentLang) throws IOException;

  public String createSelfUrl(String platform, String appTag);
}
