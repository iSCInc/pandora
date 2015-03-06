package com.wikia.discussionservice.services;

import com.wikia.discussionservice.mappers.DataStore;

import java.util.ArrayList;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

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

  public ArrayList<String> getList(int siteId, String contentType) {
    Jedis jedis = getInstance();
    ArrayList<String> keyResults = (ArrayList<String>) jedis.scan("0",
        new ScanParams().match(getKeyPattern(siteId, contentType))
    ).getResult();
    if (keyResults.isEmpty()) {
      return new ArrayList<>();
    }

    return (ArrayList<String>) jedis.mget((String[]) keyResults.toArray());
  }

  public String getKeyPattern(int siteId, String contentType) {
    return siteId + ":" + contentType + ":";
  }

  public String getKey(int siteId, String contentType, int contentId) {
    return siteId + ":" + contentType + ":" + contentId;
  }

  private Jedis getInstance() {
    return new Jedis(host, port);
  }

}
