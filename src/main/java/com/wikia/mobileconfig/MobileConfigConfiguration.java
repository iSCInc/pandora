package com.wikia.mobileconfig;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;

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

  public MobileConfigConfiguration() {
    httpClient = new HttpClientConfiguration();
  }

  public HttpClientConfiguration getHttpClientConfiguration() {
    return httpClient;
  }

  public String getAppsDeployerDomain() {
    return appsDeployerDomain;
  }
}
