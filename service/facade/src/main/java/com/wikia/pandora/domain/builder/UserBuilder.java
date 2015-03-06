package com.wikia.pandora.domain.builder;

import com.wikia.pandora.domain.User;

public class UserBuilder {

  private int id;
  private String name;
  private String avatar;
  private String url;

  private UserBuilder() {
  }

  public static UserBuilder anUser() {
    return new UserBuilder();
  }

  public UserBuilder withId(int id) {
    this.id = id;
    return this;
  }

  public UserBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public UserBuilder withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public UserBuilder withUrl(String url) {
    this.url = url;
    return this;
  }

  public UserBuilder but() {
    return anUser().withId(id).withName(name).withAvatar(avatar).withUrl(url);
  }

  public User build() {
    User user = new User(id, name, avatar, url);
    return user;
  }
}
