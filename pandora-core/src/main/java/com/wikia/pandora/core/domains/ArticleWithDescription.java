package com.wikia.pandora.core.domains;

public class ArticleWithDescription {

  private final Integer id;
  private final String title;
  private final Integer ns;
  private final String description;

  public ArticleWithDescription(Integer id, String title, Integer ns, String description
  ) {
    this.id = id;
    this.title = title;
    this.ns = ns;
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
}
