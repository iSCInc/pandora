package com.wikia.mobileconfig.service;

import com.wikia.mobileconfig.core.MobileConfiguration;

import java.io.IOException;

public interface ConfigurationService {

  public MobileConfiguration getDefault(String platform) throws IOException;

  public MobileConfiguration getConfiguration(String platform, String appTag) throws IOException;

  public String createSelfUrl(String platform, String appTag);
}
