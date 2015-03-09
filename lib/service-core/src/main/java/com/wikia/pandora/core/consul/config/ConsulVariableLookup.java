package com.wikia.pandora.core.consul.config;

import com.wikia.gradle.ConsulKeyValueConfig;

import org.apache.commons.lang3.text.StrLookup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class ConsulVariableLookup extends StrLookup {

  public static final Pattern VARIABLE_PATTERN = Pattern.compile("([\\w_]+)(:(.+))?");
  public static final String SUBSTITUTOR_PREFIX = "${consul:";

  protected ConsulKeyValueConfig keyValueConfig;

  @Inject
  public ConsulVariableLookup(@Nullable ConsulKeyValueConfig keyValueConfig) {
    this.keyValueConfig = keyValueConfig;
  }

  @Override
  public String lookup(String key) {
    Matcher matcher = VARIABLE_PATTERN.matcher(key);

    if (!matcher.matches()) {
      throw new RuntimeException(String.format("unable to parse variable: %s", key));
    }

    String variable = matcher.group(1);
    String defaultValue = matcher.group(3);
    String value = null;

    if (keyValueConfig != null) {
      value = keyValueConfig.get(variable);
    }

    if (value == null) {
      if (defaultValue != null) {
        value = defaultValue;
      } else {
        throw new RuntimeException(
            String.format("undefined variable and no default: %s", variable));
      }
    }

    return value;
  }
}
