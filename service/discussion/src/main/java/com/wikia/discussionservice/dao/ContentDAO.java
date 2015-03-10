package com.wikia.discussionservice.dao;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wikia.discussionservice.domain.Content;
import com.wikia.discussionservice.services.JedisService;

import java.io.IOException;
import java.lang.Class;
import java.lang.Exception;
import java.lang.String;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

import lombok.NonNull;

import javax.inject.Inject;

/**
 * Created by armon on 3/2/15.
 */
public class ContentDAO {
  @NonNull
  private final JedisService jedisService;

  @Inject
  public ContentDAO(JedisService jedisService) {
    this.jedisService = jedisService;
  }

  public <T extends Content> ArrayList<T> getItems(int siteId, Class<? extends Content> U) {
    ArrayList<T> contentList = new ArrayList<>();
    String type = getContentType(U);
    if (type == null) {
      return contentList;
    }

    ObjectMapper mapper = new ObjectMapper();
    Optional<ArrayList<String>> stringArrayList = Optional.ofNullable(jedisService.getList(siteId, type));
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

  public <T extends Content> T getContent(int siteId, int contentId, Class<T> U) {
    JsonParser jsonParser;
    JsonFactory jsonFactory = new JsonFactory();
    ObjectMapper mapper = new ObjectMapper();

    String type = getContentType(U);
    if (type == null) {
      return null;
    }

    String content = jedisService.get(siteId, type, contentId);
    try {
      jsonParser = jsonFactory.createParser(content);
      return mapper.readValue(jsonParser, U);
    } catch (Exception ex) {
      return null;
    }
  }

  public <T extends Content> T createContent(int siteId, T content) {
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
    jedisService.set(siteId, type, content.getId(), jsonValue);

    return content;
  }

  public <T extends Content> void deleteContent(int siteId, int contentId, Class<T> U) {
    String type = getContentType(U);
    if (type == null) {
      return;
    }
    jedisService.delete(siteId, type, contentId);
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
}
