package com.wikia.mobileconfig.exceptions;

import com.wikia.mobileconfig.core.Problem;

import com.sun.jersey.api.Responses;

import javax.ws.rs.core.Response;

public class InvalidApplicationTagException extends MobileConfigException {
  static String EXCEPTION_TITLE = "Invalid request";
  static String EXCEPTION_MESSAGE_FORMAT = "Invalid application tag (%s)";

  public InvalidApplicationTagException(String appTag) {
    super(Response.status(Responses.CLIENT_ERROR)
              .entity(new Problem(EXCEPTION_TITLE, String.format(EXCEPTION_MESSAGE_FORMAT, appTag)))
              .type(RESPONSE_TYPE)
              .build()
    );
  }
}
