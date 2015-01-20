package com.wikia.pandora.core;

public class Comment {

  private final Long id;
  private final String wikiaName;
  private final String articleName;
  private final String text;
  private final Long created;
  private final String userName;

  public Comment(Long id, String wikiaName, String articleName, String text, Long created,
                 String userName) {
    this.id = id;
    this.wikiaName = wikiaName;
    this.articleName = articleName;
    this.text = text;
    this.created = created;
    this.userName = userName;
  }


  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public Long getCreated() {
    return created;
  }

  public String getArticleName() {
    return articleName;
  }

  public String getWikiaName() {
    return wikiaName;
  }

  public String getUserName() {
    return userName;
  }

}
