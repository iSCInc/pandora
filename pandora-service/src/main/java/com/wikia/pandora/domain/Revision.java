package com.wikia.pandora.domain;


import java.util.Date;

public class Revision {

  private final int revId;
  private final int parentId;
  private final String user;
  private final Date timestamp;
  private final String comment;

  private String content;

  public Revision(int revId, int parentId, String user, Date timestamp, String comment,
                  String content) {
    this.revId = revId;
    this.parentId = parentId;
    this.user = user;
    this.timestamp = timestamp;
    this.comment = comment;
    this.content = content;
  }

  public int getRevId() {
    return revId;
  }

  public int getParentId() {
    return parentId;
  }

  public String getUser() {
    return user;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getComment() {
    return comment;
  }

  public String getContent() {
    return content;
  }
}
