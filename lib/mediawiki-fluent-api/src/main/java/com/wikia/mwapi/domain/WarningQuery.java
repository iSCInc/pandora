package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WarningQuery {

  private String message;

  @JsonProperty("*")
  public String getMessage() {
    return message;
  }
}
