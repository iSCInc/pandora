package com.wikia.pandora.core.domain;

import java.util.List;
import java.util.Map;

public class MercuryArticle {

  private final String content;
  private final String description;
  private final List<Media> media;
  private final List<Category> categories;
  private final Map<String, User> users;

  public MercuryArticle(String content, String description, List<Media> media,
                        List<Category> categories, Map<String, User> users) {
    this.content = content;
    this.description = description;
    this.media = media;
    this.categories = categories;
    this.users = users;
  }


  public String getContent() {
    return content;
  }

  public String getDescription() {
    return description;
  }

  public List<Media> getMedia() {
    return media;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public Map<String, User> getUsers() {
    return users;
  }
}

