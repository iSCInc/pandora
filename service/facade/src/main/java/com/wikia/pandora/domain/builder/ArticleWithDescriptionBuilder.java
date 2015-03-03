package com.wikia.pandora.domain.builder;

import com.wikia.pandora.domain.ArticleWithDescription;

public class ArticleWithDescriptionBuilder {

  private Integer id;
  private String title;
  private Integer ns;
  private String description;

  private ArticleWithDescriptionBuilder() {
  }

  public static ArticleWithDescriptionBuilder anArticleWithDescription() {
    return new ArticleWithDescriptionBuilder();
  }

  public ArticleWithDescriptionBuilder withId(Integer id) {
    this.id = id;
    return this;
  }

  public ArticleWithDescriptionBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public ArticleWithDescriptionBuilder withNs(Integer ns) {
    this.ns = ns;
    return this;
  }

  public ArticleWithDescriptionBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public ArticleWithDescriptionBuilder but() {
    return anArticleWithDescription().withId(id).withTitle(title).withNs(ns)
        .withDescription(description);
  }

  public ArticleWithDescription build() {
    ArticleWithDescription
        articleWithDescription =
        new ArticleWithDescription(id, title, ns, description);
    return articleWithDescription;
  }
}
