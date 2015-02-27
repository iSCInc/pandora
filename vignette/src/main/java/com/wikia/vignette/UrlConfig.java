package com.wikia.vignette;

public class UrlConfig {

  public final Boolean isArchive;
  public final Boolean replaceThumbnail;
  public final Integer timestamp;
  public final String relativePath;
  public final String pathPrefix;
  public final String bucket;
  public final String baseURL;
  public final Integer domainShardCount;


  private UrlConfig(Builder builder) {
    isArchive = builder.isArchive;
    replaceThumbnail = builder.replaceThumbnail;
    timestamp = builder.timestamp;
    relativePath = builder.relativePath;
    pathPrefix = builder.pathPrefix;
    bucket = builder.bucket;
    baseURL = builder.baseURL;
    domainShardCount = builder.domainShardCount;
  }

  public static class Builder {
    private Boolean isArchive = false;
    private Boolean replaceThumbnail = false;
    private Integer timestamp;
    private String pathPrefix = "";
    private String relativePath;
    private String bucket;
    private String baseURL = "http://vignette<SHARD>.wikia.nocookie.net";
    private Integer domainShardCount = 5;

    public Builder() {
    }

    public Builder isArchive(Boolean isArchive) {
      this.isArchive = isArchive;
      return this;
    }

    public Builder replaceThumbnail(Boolean replaceThumbnail) {
      this.replaceThumbnail = replaceThumbnail;
      return this;
    }

    public Builder timestamp(Integer timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder relativePath(String relativePath) {
      this.relativePath = relativePath;
      return this;
    }

    public Builder pathPrefix(String pathPrefix) {
      this.pathPrefix = pathPrefix;
      return this;
    }

    public Builder bucket(String bucket) {
      this.bucket = bucket;
      return this;
    }

    public Builder baseURL(String baseURL) {
      this.baseURL = baseURL;
      return this;
    }

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
