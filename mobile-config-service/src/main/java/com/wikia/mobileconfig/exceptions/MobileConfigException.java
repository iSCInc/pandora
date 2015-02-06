package com.wikia.mobileconfig.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public abstract class MobileConfigException extends WebApplicationException {
  public static String RESPONSE_TYPE = "application/problem+json";

  public MobileConfigException(Response exceptionResponse) {
    super(exceptionResponse);
  }
}
