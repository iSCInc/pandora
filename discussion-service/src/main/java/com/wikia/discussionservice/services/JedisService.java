package com.wikia.discussionservice.services;

import com.wikia.discussionservice.mappers.DataStore;

import redis.clients.jedis.Jedis;

/**
 * Created by armon on 2/27/15.
 */
public class JedisService implements DataStore {
  private String host = "localhost";

  private int port = 6379;

  @Override
  public String get(int siteId, String contentType, int contentId) {
    return getInstance().get(getKey(siteId, contentType, contentId));
  }

  @Override
  public void set(int siteId, String contentType, int contentId, String value) {
    getInstance().set(getKey(siteId, contentType, contentId), value);
  }

  public String getKey(int siteId, String contentType, int contentId) {
    return siteId + ":" + contentType + ":" + contentId;
  }

  private Jedis getInstance() {
    return new Jedis(host, port);
  }

}
