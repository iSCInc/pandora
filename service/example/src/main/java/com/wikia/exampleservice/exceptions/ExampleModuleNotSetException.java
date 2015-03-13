package com.wikia.exampleservice.exceptions;

public class ExampleModuleNotSetException extends Exception {

  public static final String
      EXPLANATION_MESSAGE =
      "Environment %s is set to (%s) and cannot be recognized. Valid values: (prod, testing)";

  public ExampleModuleNotSetException(String environmentName, String stage) {
    super(String.format(EXPLANATION_MESSAGE, environmentName, stage));
  }
}
