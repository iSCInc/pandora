package com.wikia.pandora.core.domain.mock;

import java.util.Random;

public class TopContributorMock {

  private final static Random random = new Random(0);

  private final int user_id;
  private final String title;
  private final String name;
  private final String url;
  private final int numberOfEdits;
  private final String avatar;

  public TopContributorMock() {
    user_id = random.nextInt(100000);
    String randomSuffix = String.valueOf(random.nextInt());
    title = String.format("Mockito%s", randomSuffix);
    name = String.format("Mockito%s", randomSuffix);
    url = String.format("/wiki/User:Mockito%s", randomSuffix);
    numberOfEdits = random.nextInt(100000);
    avatar =
        "http://img3.wikia.nocookie.net.__cb0/common/avatars/thumb/0/0e/someimage.png/100px-someimage.png.jpg";
  }

  public int getUser_id() {
    return user_id;
  }

  public String getTitle() {
    return title;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public int getNumberOfEdits() {
    return numberOfEdits;
  }

  public String getAvatar() {
    return avatar;
  }
}