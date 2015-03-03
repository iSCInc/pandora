package com.wikia.mobileconfig.service;

import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.utils.Translator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class ConfigurationServiceBase implements ConfigurationService {

  private final static String CONFIGURATIONS_URL_FORMAT = "/configurations/%s/apps/%s";

  protected final static String CONFIGURATION_NOT_FOUND_DEBUG_MESSAGE_FORMAT =
      "Configuration for %s not found: falling back to empty modules configuration";

  protected final static String CONFIGURATION_FOR_APP_TAG_NOT_FOUND_DEBUG_MESSAGE_FORMAT =
      "Configuration for %s not found: falling back to default configuration for %s";

  @Override
  public String createSelfUrl(String platform, String appTag) {
    return String.format(CONFIGURATIONS_URL_FORMAT, platform, appTag);
  }

  protected Object translate(String langCode, String key) {

    String translation = Translator.getInstance().translate(langCode, key);

    Map<String, String> result = new HashMap<>();
    result.put(key, translation);
    return result;
  }

  protected boolean isTranslationKey(String key) {
    return key.length() >= 3 && key.charAt(0) == '_' && key.charAt(key.length() - 1) == '_';
  }

  protected void translateNode(String langCode, Object node) {
    if (node instanceof Map) {

      Collection<Map.Entry> entries = ((Map) node).entrySet();
      for (Map.Entry entry : entries) {
        if (entry.getValue() instanceof String) {
          if (isTranslationKey((String) entry.getValue())) {
            entry.setValue(translate(langCode, (String) entry.getValue()));
          }
        } else {
          translateNode(langCode, entry.getValue());
        }
      }
    }
  }

  protected void translateConfiguration(MobileConfiguration config, String langCode) {
    for (Object module : config.getModules()) {
      translateNode(langCode, module);
    }
  }
}
