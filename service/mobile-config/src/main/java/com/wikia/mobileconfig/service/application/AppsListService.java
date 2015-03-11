package com.wikia.mobileconfig.service.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * A HTTP service interface for services which validate applications tags
 */
public interface AppsListService {

  public boolean isValidAppTag(String platform, String appTag) throws IOException;

  public List<HashMap<String, Object>> getAppList(String platform) throws IOException;

  public boolean isUp() throws IOException;
}
