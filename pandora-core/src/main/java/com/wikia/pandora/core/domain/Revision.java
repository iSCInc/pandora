package com.wikia.pandora.core.domain;


import java.util.Date;

public class Revision {

  private int revId;
  private int parentId;
  private String user;
  private Date timestamp;
  private String comment;

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
