package com.wikia.pandora.core.domain.builder;

import com.wikia.pandora.core.domain.Dimensions;
import com.wikia.pandora.core.domain.MercuryArticleDetail;
import com.wikia.pandora.core.domain.Revision;

public class MercuryArticleDetailBuilder {

  private int id;
  private String title;
  private int ns;
  private String url;
  private Revision revision;
  private int comments;
  private String type;
  private String aAbstract;
  private String thumbnail;
  private Dimensions original_dimensions;

  private MercuryArticleDetailBuilder() {
  }

  public static MercuryArticleDetailBuilder aMercuryArticleDetail() {
    return new MercuryArticleDetailBuilder();
  }

  public MercuryArticleDetailBuilder withId(int id) {
    this.id = id;
    return this;
  }

  public MercuryArticleDetailBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public MercuryArticleDetailBuilder withNs(int ns) {
    this.ns = ns;
    return this;
  }

  public MercuryArticleDetailBuilder withUrl(String url) {
    this.url = url;
    return this;
  }

  public MercuryArticleDetailBuilder withRevision(Revision revision) {
    this.revision = revision;
    return this;
  }

  public MercuryArticleDetailBuilder withComments(int comments) {
    this.comments = comments;
    return this;
  }

  public MercuryArticleDetailBuilder withType(String type) {
    this.type = type;
    return this;
  }

  public MercuryArticleDetailBuilder withAAbstract(String aAbstract) {
    this.aAbstract = aAbstract;
    return this;
  }

  public MercuryArticleDetailBuilder withThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
    return this;
  }

  public MercuryArticleDetailBuilder withOriginal_dimensions(Dimensions original_dimensions) {
    this.original_dimensions = original_dimensions;
    return this;
  }

  public MercuryArticleDetailBuilder but() {
    return aMercuryArticleDetail().withId(id).withTitle(title).withNs(ns).withUrl(url)
        .withRevision(revision).withComments(comments).withType(type).withAAbstract(aAbstract)
        .withThumbnail(thumbnail).withOriginal_dimensions(original_dimensions);
  }

  public MercuryArticleDetail build() {
    MercuryArticleDetail
        mercuryArticleDetail =
        new MercuryArticleDetail(id, title, ns, url, revision, comments, type, aAbstract, thumbnail,
                                 original_dimensions);
    return mercuryArticleDetail;
  }
}
