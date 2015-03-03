package com.wikia.pandora.domain;


public class Article {

  private final Integer id;
  private final String title;
  private final Integer ns;

  public Article(Integer id, String title, Integer ns) {
    this.id = id;
    this.title = title;
    this.ns = ns;
  }


  public Integer getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }


  public Integer getNs() {
    return ns;
  }
}
