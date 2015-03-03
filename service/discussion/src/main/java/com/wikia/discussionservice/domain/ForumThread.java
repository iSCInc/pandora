package com.wikia.discussionservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * Threads
 */
@NoArgsConstructor
public @Data class ForumThread {
  
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

}
