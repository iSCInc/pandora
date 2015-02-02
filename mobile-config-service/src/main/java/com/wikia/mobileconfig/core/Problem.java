package com.wikia.mobileconfig.core;

public class Problem {
  private String title;
  private String details;

  public Problem(String title, String details) {
    this.title = title;
    this.details = details;
  }

  public String getTitle() {
    return title;
  }

  public String getDetails() {
    return details;
  }
}
