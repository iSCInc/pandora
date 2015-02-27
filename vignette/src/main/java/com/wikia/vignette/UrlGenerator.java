package com.wikia.vignette;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;


public class UrlGenerator {

  public static final String MODE_ORIGINAL                = "original";
  public static final String MODE_THUMBNAIL               = "thumbnail";
  public static final String MODE_THUMBNAIL_DOWN          = "thumbnail-down";
  public static final String MODE_FIXED_ASPECT_RATIO      = "fixed-aspect-ratio";
  public static final String MODE_FIXED_ASPECT_RATIO_DOWN = "fixed-aspect-ratio-down";
  public static final String MODE_SCALE_TO_WIDTH          = "scale-to-width";
  public static final String MODE_TOP_CROP                = "top-crop";
  public static final String MODE_TOP_CROP_DOWN           = "top-crop-down";
  public static final String MODE_WINDOW_CROP             = "window-crop";
  public static final String MODE_WINDOW_CROP_FIXED       = "window-crop-fixed";
  public static final String MODE_ZOOM_CROP               = "zoom-crop";
  public static final String MODE_ZOOM_CROP_DOWN          = "zoom-crop-down";

  public static final String IMAGE_TYPE_AVATAR            = "avatars";
  public static final String IMAGE_TYPE_IMAGES            = "images";

  public static final String FORMAT_WEBP                  = "webp";
  public static final String FORMAT_JPG                   = "jpg";

  public static final String REVISION_LATEST              = "latest";

  public final UrlConfig config;

  public final Integer width;
  public final Integer height;

  public final String mode;
  public final String format;

  private List<NameValuePair> queryParams = new ArrayList<>();

  public final String imageType;

  public final Integer xOffset;
  public final Integer yOffset;

  public final Integer windowWidth;
  public final Integer windowHeight;


  private UrlGenerator(Builder builder) {
    config = builder.config;
    width = builder.width;
    height = builder.height;
    mode = builder.mode;
    format = builder.format;
    queryParams = builder.queryParams;
    imageType = builder.imageType;
    xOffset = builder.xOffset;
    yOffset = builder.yOffset;
    windowWidth = builder.windowWidth;
    windowHeight = builder.windowHeight;
  }

  public String path() {
    return "/" + imagePath() + modePath();
  }

  public String imagePath() {
    return String.format("%s/%s/%s/revision/%s",
                         config.bucket,
                         imageType,
                         config.relativePath,
                         getRevision()
    );
  }

  public String getRevision() {
    if (config.isArchive) {
      return String.format("%d", config.timestamp);
    } else {
      return REVISION_LATEST;
    }
  }

  public String modePath() {
    StringBuilder path = new StringBuilder();

    if (!mode.equals(MODE_ORIGINAL)) {
      path.append(String.format("/%s", mode));

      validateWidth();
      if (mode.equals(MODE_SCALE_TO_WIDTH)) {
        path.append(String.format("/%d", width));
      } else if (mode.equals(MODE_WINDOW_CROP) || mode.equals(MODE_WINDOW_CROP_FIXED)) {
        path.append(String.format("/width/%d", width));

        if (mode.equals(MODE_WINDOW_CROP_FIXED)) {
          validateHeight();
          path.append(String.format("/height/%d", height));
        }

        validateXOffset();
        validateYOffset();
        validateWindowWidth();
        validateWindowHeight();
        path.append(String.format("/x-offset/%d/y-offset/%d", xOffset, yOffset));
        path.append(String.format("/window-width/%d/window-height/%d", windowWidth, windowHeight));
      } else {
        validateHeight();
        path.append(String.format("/width/%d/height/%d", width, height));
      }
    }

    return path.toString();
  }

  protected void validateWidth() {
    validateProperty(width, "error, width must be set");
  }

  protected void validateHeight() {
    validateProperty(height, "error, height must be set");
  }

  protected void validateXOffset() {
    validateProperty(xOffset, "error, xOffset must be set");
  }

  protected void validateYOffset() {
    validateProperty(yOffset, "error, yOffset must be set");
  }

  protected void validateWindowHeight() {
    validateProperty(windowHeight, "error, windowHeight must be set");
  }

  protected void validateWindowWidth() {
    validateProperty(windowWidth, "error, windowWidth must be set");
  }

  protected void validateProperty(Object property, String message) {
    if (property == null) {
      throw new IllegalStateException(message);
    }
  }

  public String url() throws URISyntaxException {
    final URI baseUrlURI = new URI(domainShard(config.baseURL, config.relativePath));
    final String fullPath = path();
    URIBuilder uriBuilder = new URIBuilder();

    uriBuilder.setScheme(baseUrlURI.getScheme())
        .setHost(baseUrlURI.getHost())
        .setPath(fullPath);

    if (getRevision().equals(REVISION_LATEST)) {
      queryParams.add(new BasicNameValuePair("timestamp", config.timestamp.toString()));
    }

    if (!queryParams.isEmpty()) {
      uriBuilder.addParameters(queryParams);
    }

    return uriBuilder.build().toString();
  }

  public String domainShard(String host, String imagePath) {
    return host.replace("<SHARD>", computeShardString(imagePath));
  }

  public String computeShardString(String input) {
    if (config.domainShardCount == 0) {
      return "";
    }

    MessageDigest md;

    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      return "";
    }

    byte[] hash = md.digest(input.getBytes());
    Integer shard = 1 + (int)hash[0] % config.domainShardCount;
    return shard.toString();
  }


  public static final class Builder {
    private UrlConfig config;
    private Integer width;
    private Integer height;
    private String mode = UrlGenerator.MODE_ORIGINAL;
    private String format;
    private String imageType = UrlGenerator.IMAGE_TYPE_IMAGES;
    private Integer xOffset;
    private Integer yOffset;
    private Integer windowWidth;
    private Integer windowHeight;

    private List<NameValuePair> queryParams = new ArrayList<>();


    public Builder() {
    }

    private Builder mode(String mode) {
      this.mode = mode;
      return this;
    }

    public Builder format(String format) {
      this.format = format;
      return this;
    }

    public Builder pathPrefix(String pathPrefix) {
      this.queryParams.add(new BasicNameValuePair("path-prefix", pathPrefix));
      return this;
    }

    public Builder replace(Boolean replace) {
      if (replace) {
        this.queryParams.add(new BasicNameValuePair("replace", "true"));
      }
      return this;
    }

    public Builder fill(String fill) {
      this.queryParams.add(new BasicNameValuePair("fill", fill));
      return this;
    }

    public Builder config(UrlConfig config) {
      this.config = config;
      return this;
    }

    public Builder width(Integer width) {
      this.width = width;
      return this;
    }

    public Builder height(Integer height) {
      this.height = height;
      return this;
    }

    public Builder imageType(String imageType) {
      this.imageType = imageType;
      return this;
    }

    public Builder xOffset(Integer xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    public Builder yOffset(Integer yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    public Builder windowWidth(Integer windowWidth) {
      this.windowWidth = windowWidth;
      return this;
    }

    public Builder windowHeight(Integer windowHeight) {
      this.windowHeight = windowHeight;
      return this;
    }

    public Builder original() {
      return mode(UrlGenerator.MODE_ORIGINAL);
    }

    public Builder thumbnail() {
      return mode(UrlGenerator.MODE_THUMBNAIL);
    }

    public Builder thumbnailDown() {
      return mode(UrlGenerator.MODE_THUMBNAIL_DOWN);
    }

    public Builder zoomCrop() {
      return mode(UrlGenerator.MODE_ZOOM_CROP);
    }

    public Builder zoomCropDown() {
      return mode(UrlGenerator.MODE_ZOOM_CROP_DOWN);
    }

    public Builder fixedAspectRatio() {
      return mode(UrlGenerator.MODE_FIXED_ASPECT_RATIO);
    }

    public Builder fixedAspectRatioDown() {
      return mode(UrlGenerator.MODE_FIXED_ASPECT_RATIO_DOWN);
    }

    public Builder topCrop() {
      return mode(UrlGenerator.MODE_TOP_CROP);
    }

    public Builder topCropDown() {
      return mode(UrlGenerator.MODE_TOP_CROP_DOWN);
    }

    public Builder windowCrop() {
      return mode(UrlGenerator.MODE_WINDOW_CROP);
    }

    public Builder windowCropFixed() {
      return mode(UrlGenerator.MODE_WINDOW_CROP_FIXED);
    }

    public Builder scaleToWidth(Integer width) {
      mode(UrlGenerator.MODE_SCALE_TO_WIDTH);
      width(width);
      return this;
    }

    public Builder webp() {
      return format(UrlGenerator.FORMAT_WEBP);
    }

    public Builder jpeg() {
      return format(UrlGenerator.FORMAT_JPG);
    }

    public Builder avatar() {
      return imageType(UrlGenerator.IMAGE_TYPE_AVATAR);
    }


    public UrlGenerator build() {
      if (config == null) {
        throw new IllegalStateException("config cannot be null");
      }

      return new UrlGenerator(this);
    }
  }
}
