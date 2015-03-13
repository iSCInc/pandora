package com.wikia.exampleservice.guicemodule;

import com.google.inject.AbstractModule;

import com.wikia.exampleservice.exceptions.ExampleModuleNotSetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleModuleFactory {

  public static final String EXAMPLE_SERVICE_ENVIRONMENT = "EXAMPLE_SERVICE_ENVIRONMENT";
  public static final String STAGE_PROD = "prod";
  public static final String STAGE_TESTING = "testing";
  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleModuleFactory.class);

  public static AbstractModule createExampleModule() {
    AbstractModule module;
    String stage = System.getenv(EXAMPLE_SERVICE_ENVIRONMENT);

    switch (stage) {
      case STAGE_PROD:
        module = new ProdExampleModule();
        break;
      case STAGE_TESTING:
        module = new ProdExampleModule();
        break;
      default:
        ExampleModuleNotSetException cause = new ExampleModuleNotSetException(
            EXAMPLE_SERVICE_ENVIRONMENT, stage);
        LOGGER.error(String.format("%s not properly set", EXAMPLE_SERVICE_ENVIRONMENT), cause);
        throw new RuntimeException(cause);
    }

    return module;
  }
}

