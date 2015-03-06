package com.wikia.pandora.core.consul;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import com.wikia.gradle.ConsulKeyValueConfig;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Named;

public class ConsulGuiceModule extends AbstractModule {

  public static final String CONSUL_URI_ENV_KEY = "CONSUL_KEY_VALUE_CONFIG_SOURCE";

  @Override
  protected void configure() {
  }

  @Provides
  @Named("consulUri")
  URI providesConsulUri() throws URISyntaxException {
    String consulUri = System.getenv(CONSUL_URI_ENV_KEY);
    if (consulUri != null) {
      return new URI(consulUri);
    } else {
      return null;
    }
  }

  @Provides
  public ConsulKeyValueConfig providesConsulKeyValueConfig(@Named("consulUri") URI consulUri) {
    if (consulUri != null) {
      return new ConsulKeyValueConfig(consulUri.getHost(),
                                      consulUri.getPort(),
                                      consulUri.getPath().substring(1));
    } else {
      return null;
    }
  }
}
