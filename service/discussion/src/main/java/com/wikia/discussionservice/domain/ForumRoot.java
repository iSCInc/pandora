package com.wikia.discussionservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper=false)
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public @Data class ForumRoot extends Content {

  @JsonProperty
  private int id = 1;

  @JsonProperty
  @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
      include = JsonTypeInfo.As.PROPERTY,
      property = "@class")
  @NonNull
  private List<Forum> forums = new ArrayList<>();

  @JsonProperty
  private int siteId;

  public static String getType() {
    return "forumroot";
  }
}
