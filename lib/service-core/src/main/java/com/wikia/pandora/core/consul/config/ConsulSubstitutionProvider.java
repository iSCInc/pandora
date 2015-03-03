package com.wikia.pandora.core.consul.config;

import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import io.dropwizard.configuration.ConfigurationSourceProvider;

public class ConsulSubstitutionProvider implements ConfigurationSourceProvider {
  protected final ConfigurationSourceProvider baseProvider;
  protected final StrSubstitutor substitutor;

  public ConsulSubstitutionProvider(ConfigurationSourceProvider baseProvider,
                                    StrSubstitutor substitutor) {
    this.baseProvider = baseProvider;
    this.substitutor = substitutor;
  }

  @Override
  public InputStream open(String path) throws IOException {
    String config = convertStreamToString(baseProvider.open(path));
    String substitutedConfig = substitutor.replace(config);
    return new ByteArrayInputStream(substitutedConfig.getBytes());
  }

  static String convertStreamToString(InputStream is) {
    Scanner s = new Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }
}
