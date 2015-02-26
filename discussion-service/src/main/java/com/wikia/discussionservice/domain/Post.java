package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

public @Data class Post {
  
  @JsonProperty
  private int id;
  
  @JsonProperty
  private User poster;
  
  @JsonProperty
  private LocalDateTime date;
  
  @JsonProperty
  private String body;
}
