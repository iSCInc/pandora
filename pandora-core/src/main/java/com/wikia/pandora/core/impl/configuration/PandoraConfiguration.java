package com.wikia.pandora.core.impl.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PandoraConfiguration extends Configuration {

  @Valid
  @NotNull
  @JsonProperty
  private final HttpClientConfiguration httpClient;

  public PandoraConfiguration() {
    httpClient = new HttpClientConfiguration();
  }

  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }
}
