package com.wikia.vignette;

import com.google.common.base.Optional;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * UrlGenerator represents a thumbnail/original image url built using the data from a UrlConfig
 */
public class UrlGenerator {
  public static final String FORMAT_WEBP                  = "webp";
  public static final String FORMAT_JPG                   = "jpg";

  public static final String REVISION_LATEST              = "latest";

  public final UrlConfig config;

  public final Optional<Integer> width;
  public final Optional<Integer> height;

  public final ThumbnailMode mode;

  private List<NameValuePair> queryParams = new ArrayList<>();

  public final Optional<Integer> xOffset;
  public final Optional<Integer> yOffset;

  public final Optional<Integer> windowWidth;
  public final Optional<Integer> windowHeight;


  private UrlGenerator(Builder builder) {
    config = builder.config;
    width = builder.width;
    height = builder.height;
    mode = builder.mode;
    xOffset = builder.xOffset;
    yOffset = builder.yOffset;
    windowWidth = builder.windowWidth;
    windowHeight = builder.windowHeight;
    queryParams = builder.queryParams;
  }

  public String path() {
    return "/" + imagePath() + modePath();
  }

  public String imagePath() {
    return String.format("%s/%s/%s/revision/%s",
                         config.bucket,
                         config.imageType,
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

    if (!mode.equals(ThumbnailMode.ORIGINAL)) {
      path.append(String.format("/%s", mode));

      validate(width, "width");
      if (mode.equals(ThumbnailMode.SCALE_TO_WIDTH)) {
        path.append(String.format("/%d", width.get()));
      } else if (mode.equals(ThumbnailMode.WINDOW_CROP) ||
                 mode.equals(ThumbnailMode.WINDOW_CROP_FIXED)) {
        path.append(String.format("/width/%d", width.get()));

        if (mode.equals(ThumbnailMode.WINDOW_CROP_FIXED)) {
          validate(height, "height");
          path.append(String.format("/height/%d", height.get()));
        }

        validate(xOffset, "xOffset");
        validate(yOffset, "yOffset");
        validate(windowWidth, "windowWidth");
        validate(windowHeight, "windowHeight");
        path.append(String.format("/x-offset/%d/y-offset/%d", xOffset.get(), yOffset.get()));
        path.append(String.format("/window-width/%d/window-height/%d",
                                  windowWidth.get(),
                                  windowHeight.get()));
      } else {
        validate(height, "height");
        path.append(String.format("/width/%d/height/%d", width.get(), height.get()));
      }
    }

    return path.toString();
  }

  protected void validate(Optional optional, String property) {
    if (optional == null || !optional.isPresent()) {
      String message = String.format("error: %s is required for mode %s", property, mode);
      IllegalStateException e = new IllegalStateException(message);
      LoggerFactory.getLogger(UrlGenerator.class).error(message, e);
      throw e;
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
      queryParams.add(new BasicNameValuePair("cb", config.timestamp.toString()));
    }

    if (!queryParams.isEmpty()) {
      Collections.sort(
          queryParams,
          (NameValuePair nvp1, NameValuePair nvp2) -> nvp1.getName().compareTo(nvp2.getName()));
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
    private ThumbnailMode mode = ThumbnailMode.ORIGINAL;
    private Optional<Integer> width;
    private Optional<Integer> height;
    private Optional<Integer> xOffset;
    private Optional<Integer> yOffset;
    private Optional<Integer> windowWidth;
    private Optional<Integer> windowHeight;
    private List<NameValuePair> queryParams;

    public Builder(UrlConfig config) {
      if (config == null) {
        throw new IllegalStateException("config cannot be null");
      }

      this.config = config;
      queryParams = new ArrayList<>(config.queryParams);
    }

    /**
     * sets the generated image thumbnail to a specific mode
     * @param mode
     * @return UrlGenerator.Builder
     */
    public Builder mode(ThumbnailMode mode) {
      this.mode = mode;
      return this;
    }

    /**
     * convert the image to the specified format
     * @param format
     * @return UrlGenerator.Builder
     */
    public Builder format(String format) {
      queryParams.add(new BasicNameValuePair("format", format));
      return this;
    }

    /**
     * whether the generated thumbnail should be regenerated (does not affect originals)
     * @param replace
     * @return UrlGenerator.Builder
     */
    public Builder replace(Boolean replace) {
      queryParams.add(new BasicNameValuePair("replace", replace.toString()));
      return this;
    }

    /**
     * set the background color (if any) to the specified color.
     * See http://www.imagemagick.org/script/color.php
     * @param fill
     * @return UrlGenerator.Builder
     */
    public Builder fill(String fill) {
      queryParams.add(new BasicNameValuePair("fill", fill));
      return this;
    }

    /**
     * for animated gifs, capture a specific frame of animation
     * @param frame
     * @return UrlGenerator.Builder
     */
    public Builder frame(int frame) {
      queryParams.add(new BasicNameValuePair("frame", Integer.toString(frame)));
      return this;
    }

    /**
     * convenience for frame(0)
     * @return UrlGenerator.Builder
     */
    public Builder frame() {
      return frame(0);
    }

    public Builder width(Integer width) {
      return width(Optional.of(width));
    }

    public Builder width(Optional<Integer> width) {
      this.width = width;
      return this;
    }

    public Builder height(Integer height) {
      return height(Optional.of(height));
    }

    public Builder height(Optional<Integer> height) {
      this.height = height;
      return this;
    }

    /**
     * when using window-crop or window-crop-fixed modes, set the starting x value of the window
     * @param xOffset
     * @return UrlGenerator.Builder
     */
    public Builder xOffset(Integer xOffset) {
      return xOffset(Optional.of(xOffset));
    }

    public Builder xOffset(Optional<Integer> xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    /**
     * when using window-crop or window-crop-fixed modes, set the starting y value of the window
     * @param yOffset
     * @return UrlGenerator.Builder
     */
    public Builder yOffset(Integer yOffset) {
      return yOffset(Optional.of(yOffset));
    }

    public Builder yOffset(Optional<Integer> yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    /**
     * when using window-crop or window-crop-fixed modes, set the width of the window
     * @param windowWidth
     * @return UrlGenerator.Builder
     */
    public Builder windowWidth(Integer windowWidth) {
      return windowWidth(Optional.of(windowWidth));
    }

    public Builder windowWidth(Optional<Integer> windowWidth) {
      this.windowWidth = windowWidth;
      return this;
    }

    /**
     * when using window-crop or window-crop-fixed modes, set the height of the window
     * @param windowHeight
     * @return UrlGenerator.Builder
     */
    public Builder windowHeight(Integer windowHeight) {
      return windowHeight(Optional.of(windowHeight));
    }

    public Builder windowHeight(Optional<Integer> windowHeight) {
      this.windowHeight = windowHeight;
      return this;
    }

    /** Use any of the thumbnailing modes https://github.com/Wikia/vignette#thumbnailing-modes */

    public Builder original() {
      return mode(ThumbnailMode.ORIGINAL);
    }

    public Builder thumbnail() {
      return mode(ThumbnailMode.THUMBNAIL);
    }

    public Builder thumbnailDown() {
      return mode(ThumbnailMode.THUMBNAIL_DOWN);
    }

    public Builder zoomCrop() {
      return mode(ThumbnailMode.ZOOM_CROP);
    }

    public Builder zoomCropDown() {
      return mode(ThumbnailMode.ZOOM_CROP_DOWN);
    }

    public Builder fixedAspectRatio() {
      return mode(ThumbnailMode.FIXED_ASPECT_RATIO);
    }

    public Builder fixedAspectRatioDown() {
      return mode(ThumbnailMode.FIXED_ASPECT_RATIO_DOWN);
    }

    public Builder topCrop() {
      return mode(ThumbnailMode.TOP_CROP);
    }

    public Builder topCropDown() {
      return mode(ThumbnailMode.TOP_CROP_DOWN);
    }

    public Builder windowCrop() {
      return mode(ThumbnailMode.WINDOW_CROP);
    }

    public Builder windowCropFixed() {
      return mode(ThumbnailMode.WINDOW_CROP_FIXED);
    }

    public Builder scaleToWidth(Integer width) {
      mode(ThumbnailMode.SCALE_TO_WIDTH);
      width(width);
      return this;
    }

    /** convenience shortcuts for some common output formats */

    public Builder webp() {
      return format(UrlGenerator.FORMAT_WEBP);
    }

    public Builder jpeg() {
      return format(UrlGenerator.FORMAT_JPG);
    }


    public UrlGenerator build() {
      return new UrlGenerator(this);
    }
  }
}
