package com.wikia.discussionservice.services;

import javax.inject.Inject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wikia.discussionservice.mappers.DataStore;

import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Context;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;

/**
 * Created by armon on 2/27/15.
 */
public class JedisService implements DataStore {
//  @Inject
//  public JedisService(String host, String port) {
//    this.host = host;
//    this.port = port;
//  }

  @NotNull
  @Valid
  @JsonProperty
  private String host;// = "localhost";

  @NotNull
  @Valid
  @JsonProperty
  private String port;// = "6379";

  @Context
  private Jedis jedis;

  @Inject
  public JedisService() {
    jedis = new Jedis(host, Integer.parseInt(port));
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

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

  public void delete(int siteId, String contentType, int contentId) {
    Jedis jedis = getInstance();
    jedis.del(getKey(siteId, contentType, contentId));
  }

  public String getKeyPattern(int siteId, String contentType) {
    return siteId + ":" + contentType + ":";
  }

  public String getKey(int siteId, String contentType, int contentId) {
    return siteId + ":" + contentType + ":" + contentId;
  }

  public Jedis getInstance() {
//    return new Jedis(host != null ? host : "localhost", port != null ? Integer.parseInt(port) : 6379);

//    return new Jedis(host, Integer.parseInt(port));
    return jedis;
  }

}
