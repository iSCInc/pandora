package com.wikia.pandora.domain;

public class ArticleWithContent {

  private final Integer id;
  private final String title;
  private final Integer ns;
  private final String content;
  private final String description;

  public ArticleWithContent(Integer id, String title, Integer ns, String description, String content
  ) {
    this.id = id;
    this.title = title;
    this.ns = ns;
    this.content = content;
    this.description = description;
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


  public String getDescription() {
    return description;
  }

  public String getContent() {
    return content;
  }
}
