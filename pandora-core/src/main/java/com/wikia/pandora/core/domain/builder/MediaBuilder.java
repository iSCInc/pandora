package com.wikia.pandora.core.domain.builder;

import com.wikia.pandora.core.domain.Media;

public class MediaBuilder {

  private int ns;
  private String title;

  private MediaBuilder() {
  }

  public static MediaBuilder aMedia() {
    return new MediaBuilder();
  }

  public MediaBuilder withNs(int ns) {
    this.ns = ns;
    return this;
  }

  public MediaBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public MediaBuilder but() {
    return aMedia().withNs(ns).withTitle(title);
  }

  public Media build() {
    Media media = new Media(ns, title);
    return media;
  }
}
