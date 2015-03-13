package com.wikia.mobileconfig.exceptions;

public class MobileConfigModuleNotSetException extends Exception {

  public static final String
      EXPLANATION_MESSAGE =
      "Environment MOBILE_CONFIG_SERVICE_ENVIRONMENT is set to (%s) and cannot be recognize. "
      + "Set it to one of (prod, testing, qa)";

  public MobileConfigModuleNotSetException(String stage) {
    super(String.format(EXPLANATION_MESSAGE, stage));
  }
}
