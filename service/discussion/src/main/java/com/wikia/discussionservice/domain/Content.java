package com.wikia.discussionservice.domain;

/**
 * Created by armon on 3/2/15.
 */
public class Content {
  public int getId() {
    throw new ExceptionInInitializerError("Child class must implement this!");
  }
  public static String getType() {
    throw new ExceptionInInitializerError("Child class must implement this!");
  }
}
