package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown=true)
public @Data class Post {
  
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
}