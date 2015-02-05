package com.wikia.pandora.domain.builder;

import com.wikia.pandora.domain.Revision;

import java.util.Date;

public class RevisionBuilder {

  private int revId;
  private int parentId;
  private String user;
  private Date timestamp;
  private String comment;
  private String content;

  private RevisionBuilder() {
  }

  public static RevisionBuilder aRevision() {
    return new RevisionBuilder();
  }

  public RevisionBuilder withRevId(int revId) {
    this.revId = revId;
    return this;
  }

  public RevisionBuilder withParentId(int parentId) {
    this.parentId = parentId;
    return this;
  }

  public RevisionBuilder withUser(String user) {
    this.user = user;
    return this;
  }

  public RevisionBuilder withTimestamp(Date timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public RevisionBuilder withComment(String comment) {
    this.comment = comment;
    return this;
  }

  public RevisionBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public RevisionBuilder but() {
    return aRevision().withRevId(revId).withParentId(parentId).withUser(user)
        .withTimestamp(timestamp).withComment(comment).withContent(content);
  }

  public Revision build() {
    Revision revision = new Revision(revId, parentId, user, timestamp, comment, content);
    return revision;
  }
}
