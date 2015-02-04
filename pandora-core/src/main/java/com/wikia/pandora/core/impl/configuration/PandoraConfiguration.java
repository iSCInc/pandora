package com.wikia.pandora.core.impl.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PandoraConfiguration extends Configuration {

  @NotEmpty
  private String mercuryGatewayHost;

  private Integer mercuryGatewayPort = 80;

  @Valid
  @NotNull
  @JsonProperty
  private final HttpClientConfiguration httpClient;

  public PandoraConfiguration() {
    httpClient = new HttpClientConfiguration();
  }

  @JsonProperty
  public String getMercuryGatewayHost() {
    return mercuryGatewayHost;
  }

  @JsonProperty
  public void setMercuryGatewayHost(String mercuryGatewayHost) {
    this.mercuryGatewayHost = mercuryGatewayHost;
  }

  @JsonProperty
  public Integer getMercuryGatewayPort() {
    return mercuryGatewayPort;
  }

  @JsonProperty
  public void setMercuryGatewayPort(Integer mercuryGatewayPort) {
    this.mercuryGatewayPort = mercuryGatewayPort;
  }

  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }
}
