package com.wikia.pandora.core.consul;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.util.Duration;

public class ConsulConfiguration {

  @NotNull
  @Valid
  private Duration heartbeatInterval;

  @NotNull
  @Valid
  private Duration heartbeatTTL;

  @NotNull
  @Valid
  private List<String> tags;

  @NotNull
  @Valid
  private URI consulUri = URI.create("http://localhost:8500");

  public Duration getHeartbeatInterval() {
    return heartbeatInterval;
  }

  public Duration getHeartbeatTTL() {
    return heartbeatTTL;
  }

  public List<String> getTags() {
    return tags;
  }

  public URI getConsulUri() {
    return consulUri;
  }
}
