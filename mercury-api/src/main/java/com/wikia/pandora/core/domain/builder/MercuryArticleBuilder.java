package com.wikia.pandora.core.domain.builder;

import com.wikia.pandora.core.domain.Category;
import com.wikia.pandora.core.domain.Media;
import com.wikia.pandora.core.domain.MercuryArticle;
import com.wikia.pandora.core.domain.User;

import java.util.List;
import java.util.Map;

public class MercuryArticleBuilder {

  private String content;
  private String description;
  private List<Media> media;
  private List<Category> categories;
  private Map<String, User> users;

  private MercuryArticleBuilder() {
  }

  public static MercuryArticleBuilder aMercuryArticle() {
    return new MercuryArticleBuilder();
  }

  public MercuryArticleBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public MercuryArticleBuilder withDescription(String description) {
    this.description = description;
    return this;
  }

  public MercuryArticleBuilder withMedia(List<Media> media) {
    this.media = media;
    return this;
  }

  public MercuryArticleBuilder withCategories(List<Category> categories) {
    this.categories = categories;
    return this;
  }

  public MercuryArticleBuilder withUsers(Map<String, User> users) {
    this.users = users;
    return this;
  }

  public MercuryArticleBuilder but() {
    return aMercuryArticle().withContent(content).withDescription(description).withMedia(media)
        .withCategories(categories).withUsers(users);
  }

  public MercuryArticle build() {
    MercuryArticle
        mercuryArticle =
        new MercuryArticle(content, description, media, categories, users);
    return mercuryArticle;
  }
}
