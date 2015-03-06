package com.wikia.discussionservice.dao;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wikia.discussionservice.domain.Content;
import com.wikia.discussionservice.services.JedisService;

import java.io.IOException;
import java.lang.Exception;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by armon on 3/2/15.
 */
public class ContentDAO {
  private final JedisService jedisService = new JedisService();

  public <T extends Content> ArrayList<T> getItems(int siteId, Class<? extends Content> U) {
    ArrayList<T> contentList = new ArrayList<>();
    String type;
    try {
      Method method = U.getMethod("getType");
      type = method.invoke(null).toString();
    } catch (Exception ex) {
      System.out.printf("Exception in ContentDao::getItems: %s", ex.getMessage());
      return contentList;
    }

    ObjectMapper mapper = new ObjectMapper();
    Optional<ArrayList<String>> stringArrayList = Optional.ofNullable(jedisService.getList(siteId, type));
    for (String item : stringArrayList.get()) {
      System.out.printf("ContentDao::getItems: %s", item.toString());
      try {
        contentList.add(mapper.readValue(item, (Class<T>) U));
      } catch (IOException ex) {
        System.out.printf("Exception in ContentDao::getItems: %s", ex.getMessage());
        continue;
      }
    }

    return contentList;
  }

  public <T extends Content> T getContent(int siteId, int contentId, Class<T> U) {
    JsonParser jsonParser;
    JsonFactory jParser = new JsonFactory();
    ObjectMapper mapper = new ObjectMapper();

    try {
      Method method = U.getMethod("getType");
      String type = method.invoke(null).toString();
      String content = jedisService.get(siteId, type, contentId);
      jsonParser = jParser.createParser(content);
      return mapper.readValue(jsonParser, U);
    } catch (Exception ex) {
      // TODO Use proper logging & error handling
      System.out.printf("Exception in ContentDao::getContent: %s", ex.getMessage());
      return null;
    }
  }

  public <T extends Content> T createContent(int siteId, T content) {
    try {
      Method method = content.getClass().getMethod("getType");
      Object retVal = method.invoke(null);

      ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      String jsonValue = ow.writeValueAsString(content);

      System.out.printf("ContentDAO::createContent jsonValue: %s", jsonValue);
      jedisService.set(siteId, (String) retVal, content.getId(), jsonValue);
    } catch (Exception ex) {
      // TODO Use proper logging & error handling
      System.out.printf("Exception in ContentDao::createContent: %s", ex.getMessage());
      return null;
    }

    return content;
  }

  public <T extends Content> void deleteContent(int siteId, int contentId, Class<T> U) {
    String type;
    try {
      Method method = U.getMethod("getType");
      type = method.invoke(null).toString();
    } catch (Exception ex) {
      return;
    }
    jedisService.delete(siteId, type, contentId);
  }
}
