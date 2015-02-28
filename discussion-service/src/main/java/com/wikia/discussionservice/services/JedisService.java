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
  public String get(int siteId, int forumId) {
    return getInstance().get(getKey(siteId, forumId));
  }

  @Override
  public void set(int siteId, int forumId, String value) {
    getInstance().set(getKey(siteId, forumId), value);
  }

  public String getKey(int siteId, int forumId) {
    return siteId + ":forum:" + forumId;
  }

  private Jedis getInstance() {
    return new Jedis(host, port);
  }

}
