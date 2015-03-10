package com.wikia.vignette;

public enum ThumbnailMode {
  ORIGINAL("original"),
  THUMBNAIL("thumbnail"),
  THUMBNAIL_DOWN("thumbnail-down"),
  FIXED_ASPECT_RATIO("fixed-aspect-ratio"),
  FIXED_ASPECT_RATIO_DOWN("fixed-aspect-ratio-down"),
  SCALE_TO_WIDTH("scale-to-width"),
  TOP_CROP("top-crop"),
  TOP_CROP_DOWN("top-crop-down"),
  WINDOW_CROP("window-crop"),
  WINDOW_CROP_FIXED("window-crop-fixed"),
  ZOOM_CROP("zoom-crop"),
  ZOOM_CROP_DOWN("zoom-crop-down");

  private final String name;

  private ThumbnailMode(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }

  public static ThumbnailMode fromString(String thumbMode) {
    if (thumbMode != null) {
      thumbMode = thumbMode.toLowerCase();

      for (ThumbnailMode mode : ThumbnailMode.values()) {
        if (mode.name.equals(thumbMode)) {
          return mode;
        }
      }
    }

    throw new IllegalArgumentException(thumbMode);
  }
}
