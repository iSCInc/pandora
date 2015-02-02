package com.wikia.pandora.core.domain.builder;

import com.wikia.pandora.core.domain.Category;

public class CategoryBuilder {

  private int pageId;
  private int ns;
  private String title;

  private CategoryBuilder() {
  }

  public static CategoryBuilder aCategory() {
    return new CategoryBuilder();
  }

  public CategoryBuilder withPageId(int pageId) {
    this.pageId = pageId;
    return this;
  }

  public CategoryBuilder withNs(int ns) {
    this.ns = ns;
    return this;
  }

  public CategoryBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public CategoryBuilder but() {
    return aCategory().withPageId(pageId).withNs(ns).withTitle(title);
  }

  public Category build() {
    Category category = new Category(pageId, ns, title);
    return category;
  }
}
