package com.wikia.pandora.core.domain.builder;

import com.wikia.pandora.core.domain.Comment;

public class CommentBuilder {

  private Long id;
  private String wikiaName;
  private String articleName;
  private String text;
  private Long created;
  private String userName;

  private CommentBuilder() {
  }

  public static CommentBuilder aComment() {
    return new CommentBuilder();
  }

  public CommentBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public CommentBuilder withWikiaName(String wikiaName) {
    this.wikiaName = wikiaName;
    return this;
  }

  public CommentBuilder withArticleName(String articleName) {
    this.articleName = articleName;
    return this;
  }

  public CommentBuilder withText(String text) {
    this.text = text;
    return this;
  }

  public CommentBuilder withCreated(Long created) {
    this.created = created;
    return this;
  }

  public CommentBuilder withUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public CommentBuilder but() {
    return aComment().withId(id).withWikiaName(wikiaName).withArticleName(articleName)
        .withText(text).withCreated(created).withUserName(userName);
  }

  public Comment build() {
    Comment comment = new Comment(id, wikiaName, articleName, text, created, userName);
    return comment;
  }
}
