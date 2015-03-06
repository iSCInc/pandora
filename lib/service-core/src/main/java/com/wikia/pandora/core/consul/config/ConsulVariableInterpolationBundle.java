package com.wikia.pandora.core.consul.config;

import com.wikia.gradle.ConsulKeyValueConfig;

import org.apache.commons.lang3.text.StrSubstitutor;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConsulVariableInterpolationBundle implements Bundle {
  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    ConsulVariableLookup lookup = new ConsulVariableLookup(ConsulKeyValueConfig.fromEnv());
    StrSubstitutor substitutor = new StrSubstitutor(lookup)
        .setVariablePrefix(ConsulVariableLookup.SUBSTITUTOR_PREFIX);

    bootstrap.setConfigurationSourceProvider(
        new ConsulSubstitutionProvider(bootstrap.getConfigurationSourceProvider(), substitutor));
  }

  @Override
  public void run(Environment environment) {

  }
}
