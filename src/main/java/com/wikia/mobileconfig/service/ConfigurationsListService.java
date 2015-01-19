package com.wikia.mobileconfig.service;

import java.io.IOException;

/**
 * A HTTP service interface for services which validate applications tags
 */
public interface ConfigurationsListService {
    public Boolean isValidAppTag(String appTag) throws IOException;
    public Boolean isUp() throws IOException;
}
