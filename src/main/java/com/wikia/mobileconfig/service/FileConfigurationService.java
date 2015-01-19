package com.wikia.mobileconfig.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.MobileConfigApplication;

import java.io.File;
import java.io.IOException;

public class FileConfigurationService implements ConfigurationService {
    private String root;
    private ObjectMapper mapper;

    public FileConfigurationService(String root) {
        this.root = root;
        this.mapper = new ObjectMapper();
    }

    @Override
    public MobileConfiguration getDefault(String platform) throws IOException {
        MobileConfiguration configuration = this.mapper.readValue(
            new File(this.createDefaultFilePath(platform)),
            MobileConfiguration.class
        );

        return configuration;
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
                String.format(
                    "Configuration for %s not found: falling back to default configuration for %s",
                    appTag,
                    platform
                )
            );
            return getDefault(platform);
        }
    }

    @Override
    public String createSelfUrl(String platform, String appTag) {
        return String.format("/configurations/platform/%s/app/%s", platform, appTag);
    }

    private String createDefaultFilePath(String platform) {
        return String.format("%s/%s:default.json", this.root, platform);
    }

    private String createFilePath(String platform, String appTag) {
        return String.format("%s/%s:%s.json", this.root, platform, appTag);
    }
}
