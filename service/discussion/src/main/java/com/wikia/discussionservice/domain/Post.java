package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown=true)
public @Data class Post extends Content {
  
  @JsonProperty
  private int id;
  
  @JsonProperty
  private String title;
  
  @JsonProperty
  private int threadId;
  
  @JsonProperty
  private int posterId;

  @JsonProperty
  private LocalDateTime date;

  @JsonProperty
  private String body;

  public static String getType() {
    return "post";
  }

}
