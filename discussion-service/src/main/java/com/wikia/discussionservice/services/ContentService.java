package com.wikia.discussionservice.services;

import javax.inject.Inject;

import lombok.NonNull;

/**
 * Created by armon on 3/2/15.
 */
abstract public class ContentService {
  @NonNull
  private JedisService jedisService;

  @Inject
  public void setJedisService(JedisService jedisService) {
    this.jedisService = jedisService;
  }

  public ContentService() {

  }

  public String getRedisValue(int siteId, String contentType, int contentId) {
    return jedisService.get(siteId, contentType, contentId);
  }

  public void setRedisValue(int siteId, String contentType, int contentId, String value) {
    jedisService.set(siteId, contentType, contentId, value);
  }

}
