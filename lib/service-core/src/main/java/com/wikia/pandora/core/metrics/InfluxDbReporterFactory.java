package com.wikia.pandora.core.metrics;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.metrics.BaseFormattedReporterFactory;
import metrics_influxdb.InfluxdbReporter;
import metrics_influxdb.InfluxdbUdp;

import javax.validation.constraints.NotNull;

@JsonTypeName("influxdb")
public class InfluxDbReporterFactory extends BaseFormattedReporterFactory {

  @NotNull
  private String host;

  @NotNull
  private int port;

  @NotNull
  private String prefix;

  private boolean skipIdleMetrics = true;

  private boolean excludeTakesPrecedence = false;

  @JsonProperty
  public String getHost() {
    return host;
  }

  @JsonProperty
  public void setHost(String host) {
    this.host = host;
  }

  @JsonProperty
  public int getPort() {
    return port;
  }

  @JsonProperty
  public void setPort(int port) {
    this.port = port;
  }

  @JsonProperty
  public String getPrefix() {
    return prefix;
  }

  @JsonProperty
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  @JsonProperty
  public boolean getSkipIdleMetrics() {
    return skipIdleMetrics;
  }

  @JsonProperty
  public void setSkipIdleMetrics(boolean skipIdleMetrics) {
    this.skipIdleMetrics = skipIdleMetrics;
  }

  @JsonProperty
  public boolean getExcludeTakesPrecedence() {
    return excludeTakesPrecedence;
  }

  @JsonProperty
  public void setExcludeTakesPrecedence(boolean excludeTakesPrecedence) {
    this.excludeTakesPrecedence = excludeTakesPrecedence;
  }

  @Override
  public MetricFilter getFilter() {
    return new RegexFilter(getIncludes(), getExcludes(), getExcludeTakesPrecedence());
  }

  public ScheduledReporter build(MetricRegistry registry) {
    InfluxdbUdp influxdb = new InfluxdbUdp(getHost(), getPort());
    return InfluxdbReporter
        .forRegistry(registry)
        .prefixedWith(getPrefix())
        .convertRatesTo(getRateUnit())
        .convertDurationsTo(getDurationUnit())
        .filter(getFilter())
        .skipIdleMetrics(getSkipIdleMetrics())
        .build(influxdb);
  }
}
