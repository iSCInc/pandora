package com.wikia.pandora.core.builder;

import com.wikia.pandora.core.Article;

public class ArticleBuilder {

  private Integer id;
  private String title;
  private String content;

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

  public ArticleBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public ArticleBuilder but() {
    return anArticle().withId(id).withTitle(title).withContent(content);
  }

  public Article build() {
    Article article = new Article(id, title, content);
    return article;
  }
}
