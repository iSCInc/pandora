package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper=false)
public @Data class User extends Content {
  
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

  public static String getType() {
    return "user";
  }
}
