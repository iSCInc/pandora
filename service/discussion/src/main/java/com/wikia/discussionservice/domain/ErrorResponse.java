package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public @Data class ErrorResponse {
  
  @JsonProperty
  private int status;
  
  @JsonProperty
  private String message;
  
  @JsonProperty
  private int code;
  
  @JsonProperty
  private String moreInfo;
}
