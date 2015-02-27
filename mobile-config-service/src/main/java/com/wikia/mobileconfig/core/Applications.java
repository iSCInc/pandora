package com.wikia.mobileconfig.core;

import com.google.common.base.Optional;

import com.wikia.vignette.ThumbnailMode;
import com.wikia.vignette.UrlConfig;
import com.wikia.vignette.UrlGenerator;

import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Applications {
  private static final String[] IMAGE_TYPES = {"ios_image", "android_image"};
  private static final String VIGNETTE_IMAGE_SET_KEY = "vignetteimage_set";

  private final List<HashMap<String, Object>> apps;
  private Optional<String> thumbMode;
  private Optional<Integer> thumbWidth;
  private Optional<Integer> thumbHeight;

  public Applications(List<HashMap<String, Object>> apps, Optional<String> thumbMode,
                      Optional<Integer> thumbWidth, Optional<Integer> thumbHeight) {
    this.thumbMode = thumbMode;
    this.thumbWidth = thumbWidth;
    this.thumbHeight = thumbHeight;
    this.apps = modifyImages(apps);
  }

  public List<HashMap<String, Object>> getApps() {
    return apps;
  }

  protected List<HashMap<String, Object>> modifyImages(List<HashMap<String, Object>> apps) {
    apps.forEach(app -> {
      if (app.containsKey(VIGNETTE_IMAGE_SET_KEY)) {
        @SuppressWarnings("unchecked")
        List<HashMap<String, Object>> images =
            (List<HashMap<String, Object>>) app.get(VIGNETTE_IMAGE_SET_KEY);

        images.forEach(data -> {
          String type = (String) data.get("type");
          UrlConfig config = new UrlConfig.Builder()
              .bucket((String) data.get("bucket"))
              .timestamp((int) data.get("timestamp"))
              .relativePath((String) data.get("path"))
              .build();

          app.put(type, generateVignetteImageUrls(config));
        });

        app.remove(VIGNETTE_IMAGE_SET_KEY);
      }

      // make sure all image objects in response have the same properties
      Arrays.stream(IMAGE_TYPES)
          .filter(type -> app.containsKey(type) && app.get(type) instanceof String)
          .forEach(type -> app.put(type, generateVignetteImageUrls(null)));
    });

    return Collections.unmodifiableList(apps);
  }

  protected HashMap<String, String> generateVignetteImageUrls(UrlConfig config) {
    String originalUrl = null;
    String thumbUrl = null;
    HashMap<String, String> urls = new HashMap<>();

    if (config != null) {
      try {
        originalUrl = new UrlGenerator.Builder()
            .config(config)
            .build()
            .url();

        if (thumbMode.isPresent()) {
          thumbUrl = new UrlGenerator.Builder()
              .config(config)
              .mode(ThumbnailMode.fromString(thumbMode.get()))
              .width(thumbWidth)
              .height(thumbHeight)
              .build()
              .url();
        }
      } catch (URISyntaxException e) {
        LoggerFactory.getLogger(Applications.class).error("error generating urls", e);
      }
    }

    urls.put("original", originalUrl);
    urls.put("thumb", thumbUrl);

    return urls;
  }
}
