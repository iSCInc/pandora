package com.wikia.pandora.core.domains.builder;

import com.wikia.pandora.core.domains.ArticleWithContent;

public class ArticleWithContentBuilder {

  private Integer id;
  private String title;
  private Integer ns;
  private String content;
  private String description;

  private ArticleWithContentBuilder() {
  }

  public static ArticleWithContentBuilder anArticleWithContent() {
    return new ArticleWithContentBuilder();
  }

  public ArticleWithContentBuilder withId(Integer id) {
    this.id = id;
    return this;
  }

  public ArticleWithContentBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public ArticleWithContentBuilder withNs(Integer ns) {
    this.ns = ns;
    return this;
  }

  public ArticleWithContentBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public ArticleWithContentBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public ArticleWithContentBuilder but() {
    return anArticleWithContent().withId(id).withTitle(title).withNs(ns).withContent(content)
        .withDescription(description);
  }

  public ArticleWithContent build() {
    ArticleWithContent
        articleWithContent =
        new ArticleWithContent(id, title, ns, content, description);
    return articleWithContent;
  }
}
