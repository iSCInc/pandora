package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Query {

  private List<Normalized> normalized;

  private LinkedHashMap<String, Page> pages;

  private List<Page> allPages;

  private List<CategoryStat> allCategories;

  private List<Page> categorymembers;

  private Map<String, BadRevId> badRevIds;

  public List<Normalized> getNormalized() {
    return normalized;
  }

  public Map<String, Page> getPages() {
    return pages;
  }

  @JsonProperty("allpages")
  public List<Page> getAllPages() {
    return allPages;
  }

  @JsonProperty("allcategories")
  public List<CategoryStat> getAllCategories() {
    return allCategories;
  }

  public Page getFirstPage() {
    Page page = null;
    if (getPages() != null && getPages().entrySet().size() > 0) {
      page = getPages().entrySet().iterator().next().getValue();
    }
    return page;
  }

  @JsonProperty("categorymembers")
  public List<Page> getCategorymembers() {
    return categorymembers;
  }

  @JsonProperty("badrevids")
  public Map<String, BadRevId> getBadRevIds() {
    return badRevIds;
  }
}
