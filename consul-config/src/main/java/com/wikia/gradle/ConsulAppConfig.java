package com.wikia.gradle;

import com.orbitz.consul.Consul;
import com.orbitz.consul.util.ClientUtil;

import java.util.HashMap;

public class ConsulAppConfig {
  protected Consul consul;

  public ConsulAppConfig(String host, int port) {
    this(Consul.newClient(host, port));
  }

  public ConsulAppConfig(Consul consul) {
    this.consul = consul;
  }

  public HashMap<String, String> getConfig(String appName, String env) {
    String appKey = getAppKey(appName, env);
    HashMap<String, String> config = new HashMap<>();

    try {
      consul.keyValueClient().getValues(appKey).stream()
          .filter(v -> getKeyName(appKey, v.getKey()) != null)
          .forEach(v -> {
            String key = getKeyName(appKey, v.getKey());
            String value = ClientUtil.decodeBase64(v.getValue());
            config.put(key, value);
          });
    } catch (Exception e) {
      return null;
    }

    return config;
  }

  protected String getAppKey(String appName, String env) {
    return "config/"+appName+"/"+env;
  }

  protected String getKeyName(String appKey, String key) {
    String remove = appKey + "/";

    if (key.length() <= remove.length()) {
      return null;
    }

    return key.substring(remove.length());
  }
}
