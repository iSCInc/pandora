package com.wikia.discussionservice.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public @Data class Forums {

  private List<Forum> forums = new ArrayList<>();
  private int offset;
  private int limit;
  private int total;
}
