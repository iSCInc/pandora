package com.wikia.vignette;

public enum ImageType {
  IMAGES("images"),
  AVATARS("avatars");

  private final String name;

  private ImageType(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
