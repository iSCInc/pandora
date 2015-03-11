package com.wikia.discussionservice.domain;

/**
 * Created by armon on 3/2/15.
 */
public abstract class Content {
  public abstract int getId();
  public static String getType() {
    throw new ExceptionInInitializerError("Child class must implement this!");
  }
}
