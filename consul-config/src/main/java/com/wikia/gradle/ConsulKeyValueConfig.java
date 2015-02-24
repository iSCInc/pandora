package com.wikia.gradle;

import com.orbitz.consul.Consul;
import com.orbitz.consul.util.ClientUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ConsulKeyValueConfig {
  public static final String ENV_KEY = "CONSUL_KEY_VALUE_CONFIG_SOURCE";

  protected Consul consul;
  protected HashMap<String, String> config;
  protected String configFolder;

  public ConsulKeyValueConfig(String host, int port, String app, String env) {
    this(Consul.newClient(host, port), getConfigFolder(app, env));
  }

  public ConsulKeyValueConfig(String host, int port, String configFolder) {
    this(Consul.newClient(host, port), configFolder);
  }

  public ConsulKeyValueConfig(Consul consul, String app, String env) {
    this(consul, getConfigFolder(app, env));
  }

  public ConsulKeyValueConfig(Consul consul, String configFolder) {
    this.consul = consul;
    this.configFolder = configFolder;
  }

  public HashMap<String, String> getConfig() {
    if (config != null) {
      return config;
    }

    config = new HashMap<>();

    try {
      consul.keyValueClient().getValues(configFolder).stream()
          .filter(v -> getConfigKey(v.getKey()) != null)
          .forEach(v -> {
            String key = getConfigKey(v.getKey());
            String value = ClientUtil.decodeBase64(v.getValue());
            config.put(key, value);
          });
    } catch (Exception e) {
      return null;
    }

    return config;
  }

  public String get(String key) {
    return getConfig().get(key);
  }

  protected String getConfigKey(String key) {
    String remove = configFolder + "/";

    if (key.length() <= remove.length()) {
      return null;
    }

    return key.substring(remove.length());
  }

  public static ConsulKeyValueConfig fromEnv() {
    try {
      URI uri = new URI(System.getenv(ENV_KEY));
      return new ConsulKeyValueConfig(uri.getHost(),
                                      uri.getPort(),
                                      uri.getPath().substring(1));
    } catch (URISyntaxException e) {
      return null;
    }
  }

  protected static String getConfigFolder(String app, String env) {
    return "config/"+app+"/"+env;
  }
}
