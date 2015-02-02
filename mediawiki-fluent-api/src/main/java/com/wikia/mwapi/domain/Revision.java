package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Revision {

  private int revId;
  private int parentId;
  private String user;
  private Date timestamp;
  private String comment;

  private String content;

  @JsonProperty("*")
  public String getContent() {
    return content;
  }

  @JsonProperty("revid")
  public int getRevId() {
    return revId;
  }

  @JsonProperty("parentid")
  public int getParentId() {
    return parentId;
  }

  @JsonProperty("user")
  public String getUser() {
    return user;
  }

  @JsonProperty("timestamp")
  public Date getTimestamp() {
    return timestamp;
  }

  @JsonProperty("comment")
  public String getComment() {
    return comment;
  }
}