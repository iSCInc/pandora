package com.wikia.pandora.core.consul;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import com.orbitz.consul.ConsulException;
import com.wikia.gradle.ConsulKeyValueConfig;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Named;

public class ConsulGuiceModule extends AbstractModule {

  public static final String CONSUL_URI_ENV_KEY = "CONSUL_KEY_VALUE_CONFIG_SOURCE";
  public static final String RUNTIME_ENVIRONMENT_ENV_KEY = "RUNTIME_ENVIRONMENT";

  public static final String DEFAULT_ENVIRONMENT_NAME = "UNDEFINED";

  private final String applicationName;
  private String environmentName;

  public ConsulGuiceModule(String applicationName, String environmentName) {
    this.applicationName = applicationName;
    this.environmentName = environmentName;
  }

  @Override
  protected void configure() {
    bindConstant()
        .annotatedWith(Names.named("appName")).to(this.applicationName);
    bindConstant()
        .annotatedWith(Names.named("consulHost")).to("localhost");
    bindConstant()
        .annotatedWith(Names.named("consulPort")).to(8500);
  }

  @Provides
  @Named("environmentName")
  public String providesEnvironment() {
    if (this.environmentName == null) {
      this.environmentName = System.getenv(RUNTIME_ENVIRONMENT_ENV_KEY);
    }
    return this.environmentName;
  }

  @Provides
  public ConsulKeyValueConfig providesConsulKeyValueConfig(@Named("appName") String appName,
                                                           @Named("environmentName") String envName,
                                                           @Named("consulHost") String consulHost,
                                                           @Named("consulPort") Integer consulPort)
      throws URISyntaxException {
    ConsulKeyValueConfig rv = null;
    String consulUri = null;
    try {
      consulUri = System.getenv(CONSUL_URI_ENV_KEY);
      if (consulUri != null) {
        URI uri = new URI(consulUri);
        rv = new ConsulKeyValueConfig(uri.getHost(), uri.getPort(), uri.getPath().substring(1));
      } else {
        rv = new ConsulKeyValueConfig(consulHost, consulPort, appName, envName);
      }
    } catch (ConsulException ex) {
      System.err.println("Error connecting to Consul");
      System.err.println(String.format("%s,%d %s, %s", consulHost, consulPort, appName, envName));
      // if either of those are specified then this should result in fatal error
      if (consulUri != null || envName != null) {
        throw ex;
      }
    }
    return rv;
  }
}
