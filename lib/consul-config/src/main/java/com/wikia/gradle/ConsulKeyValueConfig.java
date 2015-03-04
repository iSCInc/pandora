package com.wikia.gradle;

import com.orbitz.consul.Consul;
import com.orbitz.consul.util.ClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;

public class ConsulKeyValueConfig {
  public static final String ENV_KEY = "CONSUL_KEY_VALUE_CONFIG_SOURCE";

  protected Consul consul;
  protected HashMap<String, String> config;
  protected String configFolder;

  public ConsulKeyValueConfig(String host, int port, String app, String env) {
    this(Consul.newClient(host, port), getDefaultConfigFolder(app, env));
  }

  public ConsulKeyValueConfig(String host, int port, String configFolder) {
    this(Consul.newClient(host, port), configFolder);
  }

  public ConsulKeyValueConfig(Consul consul, String app, String env) {
    this(consul, getDefaultConfigFolder(app, env));
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
          .filter(v -> getConfigKey(v.getKey()) != null) // returns null for folders
          .forEach(v -> {
            String key = getConfigKey(v.getKey());
            String value = ClientUtil.decodeBase64(v.getValue());
            config.put(key, value);
          });
    } catch (Exception e) {
      Logger logger = LoggerFactory.getLogger(ConsulKeyValueConfig.class);
      logger.warn("Could not fetch consul key/value configs", e);
    }

    return config;
  }

  public String get(String key) {
    return getConfig().get(key);
  }

  protected String getConfigKey(String key) {
    String configFolderPrefix = configFolder + "/";

    // if the key ends with "/" (meaning it's a folder), don't include it in the result set
    if (key.endsWith("/")) {
      return null;
    }

    return key.replace(configFolderPrefix, "");
  }

  public static ConsulKeyValueConfig fromEnv() {
    try {
      URI uri = new URI(System.getenv(ENV_KEY));
      return new ConsulKeyValueConfig(uri.getHost(),
                                      uri.getPort(),
                                      uri.getPath().substring(1));
    } catch (Exception e) {
      return null;
    }
  }

  protected static String getDefaultConfigFolder(String app, String env) {
    return String.format("config/%s/%s", app, env);
  }
}
