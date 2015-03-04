package com.wikia.pandora.core.impl.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PandoraConfiguration extends Configuration {

  @Valid
  @Nonnull
  private String internalProxyHost;

  @Valid
  @Nonnull
  private Integer internalProxyPort = 80;

  @Valid
  @NotNull
  @JsonProperty
  private final HttpClientConfiguration httpClient;

  public PandoraConfiguration() {
    httpClient = new HttpClientConfiguration();
  }

  @JsonProperty
  public String getInternalProxyHost() {
    return internalProxyHost;
  }

  @JsonProperty
  public void setInternalProxyHost(String internalProxyHost) {
    this.internalProxyHost = internalProxyHost;
  }

  @JsonProperty
  public Integer getInternalProxyPort() {
    return internalProxyPort;
  }

  @JsonProperty
  public void setInternalProxyPort(Integer internalProxyPort) {
    this.internalProxyPort = internalProxyPort;
  }

  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }
}
