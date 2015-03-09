package com.wikia.discussionservice.views;

import com.wikia.discussionservice.resources.ForumResource;
import com.wikia.discussionservice.resources.PostResource;
import com.wikia.discussionservice.resources.ThreadResource;

import io.dropwizard.views.View;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResourceView extends View {

  @NonNull
  private final Map<String, String> relMap;
  
  private static Map<Class, String> TEMPLATE_MAP;

  static {
    TEMPLATE_MAP = new HashMap<>();
    TEMPLATE_MAP.put(ForumResource.class, "forumDocumentation.ftl");
    TEMPLATE_MAP.put(ThreadResource.class, "threadDocumentation.ftl");
    TEMPLATE_MAP.put(PostResource.class, "postDocumentation.ftl");
  }

  public static ResourceView createResourceView(Map<String, String> relMap, Class resourceClass) {
    String templateName = TEMPLATE_MAP.get(resourceClass);
    return new ResourceView(templateName, relMap);
  }

  private ResourceView(String templateName, Map<String, String> relMap) {
    super(templateName);
    this.relMap = relMap;
  }

}
