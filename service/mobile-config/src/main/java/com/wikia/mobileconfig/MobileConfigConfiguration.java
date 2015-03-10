package com.wikia.mobileconfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

public class MobileConfigConfiguration extends Configuration
    implements ProvidesConsulConfiguration {

  @Valid
  @NotNull
  @JsonProperty
  private HttpClientConfiguration httpClient;

  @NotEmpty
  @NotNull
  private String appsDeployerDomain;

  @JsonProperty
  @NotNull
  private Integer cacheTime;

  @NotEmpty
  @NotNull
  private String cephDomain;

  @NotEmpty
  @NotNull
  private String cephPort;

  @Valid
  @JsonProperty
  private ConsulConfiguration consulConfiguration;

  public MobileConfigConfiguration() {
    httpClient = new HttpClientConfiguration();
  }

  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }

  public String getAppsDeployerDomain() {
    return appsDeployerDomain;
  }

  public Integer getCacheTime() {
    return cacheTime;
  }

  public String getCephDomain() {
    return cephDomain;
  }

  public String getCephPort() {
    return cephPort;
  }

  public ConsulConfiguration getConsulConfiguration() {
    return consulConfiguration;
  }

  public void setConsulConfiguration(ConsulConfiguration consulConfiguration) {
    this.consulConfiguration = consulConfiguration;
  }
}
