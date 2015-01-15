package com.wikia.pandora.core;

import java.util.List;


public class Comment {

  private Long id;
  private String wikiaName;
  private String articleName;
  private String text;
  private Long created;
  private String userName;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public String getArticleName() {
    return articleName;
  }

  public void setArticleName(String articleName) {
    this.articleName = articleName;
  }

  public String getWikiaName() {
    return wikiaName;
  }

  public void setWikiaName(String wikiName) {
    this.wikiaName = wikiName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
