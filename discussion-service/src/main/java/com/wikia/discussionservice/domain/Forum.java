package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Domain model that represents a Forum object
 */
public @Data class Forum {

  @JsonProperty
  public int id;

  @JsonProperty
  public String name;

  @JsonProperty
  @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
      include = JsonTypeInfo.As.PROPERTY,
      property = "@class")
  public List<Forum> children;

}
