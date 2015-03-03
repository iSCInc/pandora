package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

public @Data class User {
  
  @JsonProperty
  private int id;
  
  @JsonProperty
  private String name;
  
  @JsonProperty
  private LocalDateTime joinDate;
  
  @JsonProperty
  private LocalDateTime lastOnline;
  
  @JsonProperty
  private int forumPostCount;
  
  @JsonProperty
  private String avatar;
  
  
}
