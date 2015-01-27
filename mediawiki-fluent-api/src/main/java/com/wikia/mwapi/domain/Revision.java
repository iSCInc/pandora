package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Revision {


  private String content;

  @JsonProperty("*")
  public String getContent() {
    return content;
  }
}