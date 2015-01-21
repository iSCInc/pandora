package com.wikia.mobileconfig.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.core.NullMobileConfiguration;

import java.io.File;
import java.io.IOException;

public class FileConfigurationService implements ConfigurationService {

  private final static String CONFIGURATION_NOT_FOUND_DEBUG_MESSAGE_FORMAT =
      "Configuration for %s not found: falling back to default configuration for %s";

  private final static String CONFIGURATIONS_URL_FORMAT = "/configurations/platform/%s/app/%s";

  private final static String CONFIGURATION_DEFAULT_PATH_FORMAT = "%s/%s:default.json";
  private final static String CONFIGURATION_PATH_FORMAT = "%s/%s:%s.json";

  private String root;
  private ObjectMapper mapper;

  public FileConfigurationService(String root) {
    this.root = root;
    this.mapper = new ObjectMapper();
  }

  @Override
  public MobileConfiguration getDefault(String platform) throws IOException {
    try {
      return this.mapper.readValue(
          new File(this.createDefaultFilePath(platform)),
          MobileConfiguration.class
      );
    } catch (IOException e) {
      return new NullMobileConfiguration();
    }
  }

  @Override
  public MobileConfiguration getConfiguration(String platform, String appTag) throws IOException {
    try {
      MobileConfiguration configuration = this.mapper.readValue(
          new File(this.createFilePath(platform, appTag)),
          MobileConfiguration.class
      );

      return configuration;
    } catch (IOException e) {
      MobileConfigApplication.logger.info(
          String.format(CONFIGURATION_NOT_FOUND_DEBUG_MESSAGE_FORMAT, appTag, platform)
      );

      return getDefault(platform);
    }
  }

  @Override
  public String createSelfUrl(String platform, String appTag) {
    return String.format(CONFIGURATIONS_URL_FORMAT, platform, appTag);
  }

  private String createDefaultFilePath(String platform) {
    return String.format(CONFIGURATION_DEFAULT_PATH_FORMAT, this.root, platform);
  }

  private String createFilePath(String platform, String appTag) {
    return String.format(CONFIGURATION_PATH_FORMAT, this.root, platform, appTag);
  }
}
