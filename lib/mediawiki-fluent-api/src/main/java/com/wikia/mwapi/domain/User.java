package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

  private Integer userId;
  private String name;

  @JsonProperty("userid")
  public Integer getUserId() {
    return userId;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }
}
