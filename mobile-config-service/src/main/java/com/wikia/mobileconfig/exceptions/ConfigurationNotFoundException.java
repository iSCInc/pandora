package com.wikia.mobileconfig.exceptions;

import com.wikia.mobileconfig.core.Problem;

import javax.ws.rs.core.Response;

public class ConfigurationNotFoundException extends MobileConfigException {
  static final String EXCEPTION_MESSAGE_FORMAT = "Configuration for %s on %s could not be found";

  public ConfigurationNotFoundException(String platform, String appTag) {
    super(
        Response.status(Response.Status.NOT_FOUND)
            .entity(new Problem("Not found", String.format(EXCEPTION_MESSAGE_FORMAT, appTag, platform)))
            .type(RESPONSE_TYPE)
            .build()
    );
  }
}
