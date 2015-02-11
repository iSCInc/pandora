package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BadRevId {

  private long revid;

  @JsonProperty("revid")
  public long getRevid() {
    return revid;
  }
}
