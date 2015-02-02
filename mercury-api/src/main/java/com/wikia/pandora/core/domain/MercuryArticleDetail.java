package com.wikia.pandora.core.domain;

public class MercuryArticleDetail {

  private final int id;
  private final String title;
  private final int ns;
  private final String url;
  private final Revision revision;
  private final int comments;
  private final String type;
  private final String aAbstract;
  private final String thumbnail;
  private final Dimensions original_dimensions;

  public MercuryArticleDetail(int id, String title, int ns, String url, Revision revision,
                              int comments, String type, String aAbstract, String thumbnail,
                              Dimensions original_dimensions) {
    this.id = id;
    this.title = title;
    this.ns = ns;
    this.url = url;
    this.revision = revision;
    this.comments = comments;
    this.type = type;
    this.aAbstract = aAbstract;
    this.thumbnail = thumbnail;
    this.original_dimensions = original_dimensions;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public int getNs() {
    return ns;
  }

  public String getUrl() {
    return url;
  }

  public Revision getRevision() {
    return revision;
  }

  public int getComments() {
    return comments;
  }

  public String getType() {
    return type;
  }

  public String getaAbstract() {
    return aAbstract;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public Dimensions getOriginal_dimensions() {
    return original_dimensions;
  }
}
