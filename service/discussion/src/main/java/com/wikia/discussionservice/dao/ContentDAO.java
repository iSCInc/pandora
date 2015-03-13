package com.wikia.discussionservice.dao;

import com.bendb.dropwizard.redis.JedisFactory;
import com.bendb.dropwizard.redis.JedisPoolManager;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wikia.discussionservice.domain.Content;
import com.wikia.discussionservice.services.JedisService;
//import com.wikia.jedis.JedisFactory;
//import com.wikia.jedis.JedisPoolManager;

import java.io.IOException;
import java.lang.Class;
import java.lang.Exception;
import java.lang.String;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

import lombok.NonNull;
import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;

import javax.inject.Inject;
import javax.ws.rs.core.Context;

/**
 * Created by armon on 3/2/15.
 */
public class ContentDAO {
//  @NonNull
//  private final JedisService jedisService;

//  @NonNull
//  private final JedisPoolManager jedis;
//
//  @Inject
//  public ContentDAO(Jedis jedis) {
//    this.jedis = jedis;
//  }

//  public ContentDAO() {
//    jedis = new JedisPoolManager(new JedisPool("localhost", 6379));
//  }

//  @Inject
//  public ContentDAO() {
//
//  }

  public <T extends Content> ArrayList<T> getItems(Jedis jedis, int siteId, Class<? extends Content> U) {
    ArrayList<T> contentList = new ArrayList<>();
    String type = getContentType(U);
    if (type == null) {
      return contentList;
    }

    ObjectMapper mapper = new ObjectMapper();
    Optional<ArrayList<String>> stringArrayList = Optional.ofNullable(/*jedisService.*/getList(jedis, siteId, type));
    if (stringArrayList.isPresent()) {
      for (String item : stringArrayList.get()) {
        System.out.printf("ContentDao::getItems: %s", item.toString());
        try {
          contentList.add(mapper.readValue(item, (Class<T>) U));
        } catch (IOException ex) {
          System.out.printf("Exception in ContentDao::getItems: %s", ex.getMessage());
          continue;
        }
      }
    }

    return contentList;
  }

  public <T extends Content> T getContent(Jedis jedis, int siteId, int contentId, Class<T> U) {
    JsonParser jsonParser;
    JsonFactory jsonFactory = new JsonFactory();
    ObjectMapper mapper = new ObjectMapper();

    String type = getContentType(U);
    if (type == null) {
      return null;
    }

    String content = /*jedisService.*/get(jedis, siteId, type, contentId);
    try {
      jsonParser = jsonFactory.createParser(content);
      return mapper.readValue(jsonParser, U);
    } catch (Exception ex) {
      return null;
    }
  }

  public <T extends Content> T createContent(Jedis jedis, int siteId, T content) {
    String type = getContentType(content.getClass());
    if (type == null) {
      return null;
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String jsonValue;
    try {
      jsonValue = ow.writeValueAsString(content);
    } catch (Exception ex) {
      return null;
    }

    System.out.printf("ContentDAO::createContent jsonValue: %s", jsonValue);
    /*jedisService.*/set(jedis, siteId, type, content.getId(), jsonValue);

    return content;
  }

  public <T extends Content> void deleteContent(Jedis jedis, int siteId, int contentId, Class<T> U) {
    String type = getContentType(U);
    if (type == null) {
      return;
    }
    /*jedisService.*/delete(jedis, siteId, type, contentId);
  }

  private <T extends Content> String getContentType(Class<T> U) {
    try {
      Method method = U.getMethod("getType");
      return method.invoke(null).toString();
    } catch (Exception ex) {
      System.out.printf("Exception in ContentDao::getContentType: %s", ex.getMessage());
      return null;
    }
  }

  // ----- Jedis ------

  public String get(Jedis jedis, int siteId, String contentType, int contentId) {
    return jedis.get(getKey(siteId, contentType, contentId));
  }

  public void set(Jedis jedis, int siteId, String contentType, int contentId, String value) {
    jedis.set(getKey(siteId, contentType, contentId), value);
  }

  public ArrayList<String> getList(Jedis jedis, int siteId, String contentType) {
//    Jedis jedis = getInstance();
    ArrayList<String> keyResults = (ArrayList<String>)
        jedis.scan("0", new ScanParams().match(getKeyPattern(siteId, contentType))
    ).getResult();
    if (keyResults.isEmpty()) {
      return new ArrayList<>();
    }

    return (ArrayList<String>) jedis.mget((String[]) keyResults.toArray());
  }

  public void delete(Jedis jedis, int siteId, String contentType, int contentId) {
//    Jedis jedis = getInstance();
    jedis.del(getKey(siteId, contentType, contentId));
  }

  public String getKeyPattern(int siteId, String contentType) {
    return siteId + ":" + contentType + ":";
  }

  public String getKey(int siteId, String contentType, int contentId) {
    return siteId + ":" + contentType + ":" + contentId;
  }

//  public Jedis getInstance() {
////    return new Jedis(host, Integer.parseInt(port));
//    return new JedisPool("localhost", 6379).;
//  }

}
