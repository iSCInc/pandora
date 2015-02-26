package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NonNull;

import java.util.List;

/**
 * Domain model that represents a Forum object
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public @Data class Forum {

  private Forum() {
    // Jackson deserialization
  }

  @JsonProperty
  public int id;

  @JsonProperty
  @NonNull
  public String name;
  
  @JsonProperty
  @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
      include = JsonTypeInfo.As.PROPERTY,
      property = "@class")
  @NonNull
  public List<Forum> children;
  
  @JsonProperty
  @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class")
  @NonNull
  public List<ForumThread> threads;
 
  public boolean hasChildren() {
    return getChildren() != null && !getChildren().isEmpty();
  }

  public boolean hasThreads() {
    return getThreads() != null && !getThreads().isEmpty();
  }

}
