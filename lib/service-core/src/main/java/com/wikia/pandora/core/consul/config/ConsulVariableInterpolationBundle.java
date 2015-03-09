package com.wikia.pandora.core.consul.config;

import org.apache.commons.lang3.text.StrSubstitutor;

import javax.inject.Inject;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConsulVariableInterpolationBundle implements Bundle {

  private ConsulVariableLookup lookup;

  @Inject
  public ConsulVariableInterpolationBundle(ConsulVariableLookup lookup) {
    this.lookup = lookup;
  }

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    StrSubstitutor substitutor = new StrSubstitutor(this.lookup)
        .setVariablePrefix(ConsulVariableLookup.SUBSTITUTOR_PREFIX);

    bootstrap.setConfigurationSourceProvider(
        new ConsulSubstitutionProvider(bootstrap.getConfigurationSourceProvider(), substitutor));
  }

  @Override
  public void run(Environment environment) {

  }
}
