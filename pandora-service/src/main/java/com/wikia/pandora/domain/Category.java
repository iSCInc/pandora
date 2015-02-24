package com.wikia.pandora.domain;


public class Category {

  private final int pageId;
  private final int ns;
  private final String title;

  public Category(int pageId, int ns, String title) {
    this.pageId = pageId;
    this.ns = ns;
    this.title = title;
  }

  public int getPageId() {
    return pageId;
  }

  public int getNs() {
    return ns;
  }

  public String getTitle() {
    return title;
  }
}
