package com.wikia.vignette;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * UrlConfig represents the core data needed to reference where a Vignette image lives in CEPH
 */
public class UrlConfig {

  public final Boolean isArchive;
  public final Integer timestamp;
  public final String relativePath;
  public final String bucket;
  public final String baseURL;
  public final Integer domainShardCount;
  public final ImageType imageType;
  public final ArrayList<NameValuePair> queryParams;

  private UrlConfig(Builder builder) {
    isArchive = builder.isArchive;
    timestamp = builder.timestamp;
    relativePath = builder.relativePath;
    bucket = builder.bucket;
    baseURL = builder.baseURL;
    domainShardCount = builder.domainShardCount;
    queryParams = builder.queryParams;
    imageType = builder.imageType;
  }

  public static class Builder {
    private ArrayList<NameValuePair> queryParams = new ArrayList<>();
    private Boolean isArchive = false;
    private ImageType imageType = ImageType.IMAGES;
    private Integer timestamp;
    private String relativePath;
    private String bucket;
    private String baseURL = "http://vignette<SHARD>.wikia.nocookie.net";
    private Integer domainShardCount = 5;

    public Builder() {
    }

    /**
     * sets whether this image is an archived image (older revision)
     * @param isArchive
     * @return UrlConfig.Builder
     */
    public Builder isArchive(Boolean isArchive) {
      this.isArchive = isArchive;
      return this;
    }

    /**
     * sets the timestamp of the image, both for cachebusting and for determining which revision
     * (if any) of an image to fetch
     * @param timestamp
     * @return UrlConfig.Builder
     */
    public Builder timestamp(Integer timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * top-dir/middle-dir/filename of this file, ex: "a/ab/myFile.jpg". This excludes anything
     * special and edge case, like language and zone
     * @param relativePath
     * @return UrlConfig.Builder
     */
    public Builder relativePath(String relativePath) {
      this.relativePath = relativePath;
      return this;
    }

    /**
     * which "zone" the image lives in (temp, math, etc)
     * @param zone
     * @return UrlConfig.Builder
     */
    public Builder zone(String zone) {
      queryParams.add(new BasicNameValuePair("zone", zone));
      return this;
    }

    /**
     * any other edge-case directory this image lives in (can be language or arbitrary path)
     * @param pathPrefix
     * @return UrlConfig.Builder
     */
    public Builder pathPrefix(String pathPrefix) {
      queryParams.add(new BasicNameValuePair("path-prefix", pathPrefix));
      return this;
    }

    /**
     * the top-level bucket this image lives in
     * @param bucket
     * @return UrlConfig.Builder
     */
    public Builder bucket(String bucket) {
      this.bucket = bucket;
      return this;
    }

    /**
     * the protocol, host, port, and domain shard placeholder for generating image urls
     * @param baseURL
     * @return UrlConfig.Builder
     */
    public Builder baseURL(String baseURL) {
      this.baseURL = baseURL;
      return this;
    }

    /**
     * the "type" of image (also a directory in CEPH)
     * @param imageType
     * @return
     */
    public Builder imageType(ImageType imageType) {
      this.imageType = imageType;
      return this;
    }

    public Builder avatar() {
      return imageType(ImageType.AVATARS);
    }

    /**
     * How many domains are available to shard requests across.
     *
     * Results in splitting the baseUrl across N domains, replacing the <SHARD> substring in
     * baseUrl
     * @param domainShardCount
     * @return UrlConfig.Builder
     */
    public Builder domainShardCount(Integer domainShardCount) {
      this.domainShardCount = domainShardCount;
      return this;
    }

    public UrlConfig build() {
      UrlConfig config = new UrlConfig(this);

      if (config.timestamp == null) {
        throw new IllegalStateException("timestamp cannot be null");
      }

      if (config.relativePath == null) {
        throw new IllegalStateException("relativePath cannot be null");
      }

      if (config.bucket == null) {
        throw new IllegalStateException("bucket cannot be null");
      }

      if (config.baseURL == null) {
        throw new IllegalStateException("baseURL cannot be null");
      }

      if (config.domainShardCount == null) {
        throw new IllegalStateException("domainShardCount cannot be null");
      }


      return config;
    }

  }
}
