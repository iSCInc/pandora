package com.wikia.notificationsservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
  private String text;

  public Notification(String text) {
    this.text = text;
  }

  public Notification() {
  }

  ;

  @JsonProperty
  public String getText() {
    return text;
  }

  @JsonProperty
  public void setText(String text) {
    this.text = text;
  }
}
