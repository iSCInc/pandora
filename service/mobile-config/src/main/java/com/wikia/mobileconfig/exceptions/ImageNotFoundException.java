package com.wikia.mobileconfig.exceptions;

import com.wikia.mobileconfig.core.Problem;

import javax.ws.rs.core.Response;

public class ImageNotFoundException extends MobileConfigException {
  static final String EXCEPTION_MESSAGE_FORMAT = "Image with the given name %s could not be found";

  public ImageNotFoundException(String filename) {
    super(
        Response.status(Response.Status.NOT_FOUND.getStatusCode())
            .entity(new Problem("Not found", String.format(EXCEPTION_MESSAGE_FORMAT, filename)))
            .type(RESPONSE_TYPE)
            .build()
    );
  }
}
