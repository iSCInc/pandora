package com.wikia.pandora.core.domain;


public class Category {

  private int pageId;
  private int ns;
  private String title;

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
