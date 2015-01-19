package com.wikia.pandora.core;

public class User {

  private final int id;
  private final String name;
  private final String avatar;
  private final String url;

  public User(int id, String name, String avatar, String url) {
    this.id = id;
    this.name = name;
    this.avatar = avatar;
    this.url = url;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAvatar() {
    return avatar;
  }

  public String getUrl() {
    return url;
  }

}
