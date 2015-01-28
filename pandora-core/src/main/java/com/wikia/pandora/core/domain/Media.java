package com.wikia.pandora.core.domain;


public class Media {

  private int ns;
  private String title;

  public Media(int ns, String title) {
    this.ns = ns;
    this.title = title;
  }

  public int getNs() {
    return ns;
  }

  public String getTitle() {
    return title;
  }
}
