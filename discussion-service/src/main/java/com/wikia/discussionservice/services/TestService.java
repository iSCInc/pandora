package com.wikia.discussionservice.services;

import javax.inject.Inject;

import lombok.NonNull;

public class TestService extends ContentService {

  public TestService() {
    super();
  }

  public static String getType() {
    return "test";
  }
}
