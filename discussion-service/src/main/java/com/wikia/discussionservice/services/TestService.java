package com.wikia.discussionservice.services;

import javax.inject.Inject;

import lombok.NonNull;

/**
 * Forum Service
 * Responsible for handling all actions around
 * the forum domain.
 */
public class TestService {

  @NonNull
  private final JedisService jedisService;

  @Inject
  public TestService(JedisService jedisService) {
    this.jedisService = jedisService;
  }

  public String getValue(int siteId, int key) {
    return jedisService.get(siteId, key);
  }

  public void createValue(int siteId, int key, String value) {
    jedisService.set(siteId, key, value);
  }

}
