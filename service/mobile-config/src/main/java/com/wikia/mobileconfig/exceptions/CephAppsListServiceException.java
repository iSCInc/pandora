package com.wikia.mobileconfig.exceptions;

import com.wikia.mobileconfig.core.Problem;

import javax.ws.rs.core.Response;

public class CephAppsListServiceException extends MobileConfigException {

  public CephAppsListServiceException(String message, String details) {
    super(Response.status(Response.Status.NOT_FOUND)
              .entity(new Problem(message,
                                  details))
              .type(RESPONSE_TYPE)
              .build());
  }
}
