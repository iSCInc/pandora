package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * Threads
 */
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public @Data class ForumThread extends Content {
  
  @JsonProperty
  private int id;
  
  @JsonProperty
  private int forumId;
  
  @JsonProperty
  @NonNull
  private String title;

  @JsonProperty
  @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
      include = JsonTypeInfo.As.PROPERTY,
      property = "@class")
  @NonNull
  private List<Post> posts;
  
  @JsonProperty
  @NonNull
  private User threadStarter;

  @JsonProperty
  private Post lastPost;

  public static String getType() {
    return "thread";
  }
}
