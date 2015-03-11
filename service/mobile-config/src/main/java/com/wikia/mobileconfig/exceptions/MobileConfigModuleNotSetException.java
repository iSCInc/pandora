package com.wikia.mobileconfig.exceptions;

public class MobileConfigModuleNotSetException extends Exception {

  public static final String
      EXPLANATION_MESSAGE =
      "Enviroment MOBILE_CONFIG_SERVICE_ENVIRONMENT is set as (%s), and it cannot be recognize. "
      + "Set it as one of (prod, testing, qa)";

  public MobileConfigModuleNotSetException(String stage) {
    super(String.format(EXPLANATION_MESSAGE, stage));
  }
}
