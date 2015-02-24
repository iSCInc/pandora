package com.wikia.pandora.core.consul.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.pandora.core.consul.config.ConsulConfigurationFactory;

import javax.validation.Validator;

import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.ConfigurationFactoryFactory;

public class ConsulConfigurationFactoryFactory<T> implements ConfigurationFactoryFactory<T> {
  @Override
  public ConfigurationFactory<T> create(final Class klass, final Validator validator, final ObjectMapper objectMapper,
                                     final String propertyPrefix) {
    return new ConsulConfigurationFactory<T>(klass, validator, objectMapper, propertyPrefix);
  }
}
