package com.wikia.mobileconfig.service;

public abstract class ConfigurationServiceBase implements ConfigurationService {
  private final static String CONFIGURATIONS_URL_FORMAT = "/configurations/platform/%s/app/%s";

  protected final static String CONFIGURATION_NOT_FOUND_DEBUG_MESSAGE_FORMAT =
      "Configuration for %s not found: falling back to default configuration for %s";

  @Override
  public String createSelfUrl(String platform, String appTag) {
    return String.format(CONFIGURATIONS_URL_FORMAT, platform, appTag);
  }
}
