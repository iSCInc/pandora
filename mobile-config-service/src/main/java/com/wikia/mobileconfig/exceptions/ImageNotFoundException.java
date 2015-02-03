package com.wikia.mobileconfig.exceptions;

import com.sun.jersey.api.Responses;
import com.wikia.mobileconfig.core.Problem;

import javax.ws.rs.core.Response;

public class ImageNotFoundException extends MobileConfigException {
  static String EXCEPTION_MESSAGE_FORMAT = "Image with the given name %s could not be found";

  public ImageNotFoundException(String filename) {
    super(
        Response.status(Responses.NOT_FOUND)
            .entity(new Problem("Not found", String.format(EXCEPTION_MESSAGE_FORMAT, filename)))
            .type(RESPONSE_TYPE)
            .build()
    );
  }
}
