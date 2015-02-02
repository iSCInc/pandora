package com.wikia.mobileconfig.exceptions;

import com.sun.jersey.api.Responses;
import com.wikia.mobileconfig.core.Problem;

import javax.ws.rs.core.Response;

public class ConfigurationNotFoundException extends MobileConfigException {
  static String EXCEPTION_MESSAGE_FORMAT = "Configuration for %s on %s could not be found";

  public ConfigurationNotFoundException(String platform, String appTag) {
    super(
        Response.status(Responses.NOT_FOUND)
            .entity(new Problem("Not found", String.format(EXCEPTION_MESSAGE_FORMAT, appTag, platform)))
            .type(RESPONSE_TYPE)
            .build()
    );
  }
}
