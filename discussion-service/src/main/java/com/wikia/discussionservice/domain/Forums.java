package com.wikia.discussionservice.domain;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public @Data class Forums {

  private List<Forum> forums = new ArrayList<>();
  private int start;
  private int total;
}
