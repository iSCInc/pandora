package com.wikia.pandora.core.domain.builder;

import com.wikia.pandora.core.domain.Article;

public class ArticleBuilder {

  private Integer id;
  private String title;
  private Integer ns;

  private ArticleBuilder() {
  }

  public static ArticleBuilder anArticle() {
    return new ArticleBuilder();
  }

  public ArticleBuilder withId(Integer id) {
    this.id = id;
    return this;
  }

  public ArticleBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public ArticleBuilder withNs(Integer ns) {
    this.ns = ns;
    return this;
  }

  public ArticleBuilder but() {
    return anArticle().withId(id).withTitle(title).withNs(ns);
  }

  public Article build() {
    Article article = new Article(id, title, ns);
    return article;
  }
}
