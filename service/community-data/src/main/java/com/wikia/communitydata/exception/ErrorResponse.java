package com.wikia.communitydata.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Response;

public class ErrorResponse {
  private String message;

  public ErrorResponse(String message) {
    this.message = message;
  }

  @JsonProperty
  public String getMessage() {
    return message;
  }

  @JsonProperty
  public int getStatus() {
    return Response.Status.NOT_FOUND.getStatusCode();
  }
}
