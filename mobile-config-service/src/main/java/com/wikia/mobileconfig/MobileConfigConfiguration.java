package com.wikia.mobileconfig;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wikia.pandora.core.consul.ConsulConfig;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class MobileConfigConfiguration extends Configuration {

  @Valid
  @NotNull
  @JsonProperty
  private HttpClientConfiguration httpClient;

  @NotEmpty
  @NotNull
  private String appsDeployerDomain;

  @NotEmpty
  @NotNull
  private String cephDomain;

  @NotEmpty
  @NotNull
  private String cephPort;

  @Valid
  @NotNull
  @JsonProperty
  private ConsulConfig consulConfig = new ConsulConfig();

  public MobileConfigConfiguration() {
    httpClient = new HttpClientConfiguration();
  }

  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }

  public String getAppsDeployerDomain() {
    return appsDeployerDomain;
  }

  public String getCephDomain() {
    return cephDomain;
  }

  public String getCephPort() {
    return cephPort;
  }

  public ConsulConfig getConsulConfig() {
    return consulConfig;
  }
}
