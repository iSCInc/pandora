package com.wikia.mobileconfig.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * A HTTP service interface for services which validate applications tags
 */
public interface AppsListService {

  public Boolean isValidAppTag(String appTag) throws IOException;

  public List<HashMap<String, Object>> getAppList(String platform) throws IOException;

  public Boolean isUp() throws IOException;
}
