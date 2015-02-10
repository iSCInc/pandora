package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Query {

  private ArrayList<Normalized> normalized;

  private LinkedHashMap<String, Page> pages;

  private List<Page> allPages;

  private List<Page> categorymembers;

  private LinkedHashMap<String,BadRevId> badRevIds;

  public ArrayList<Normalized> getNormalized() {
    return normalized;
  }

  public LinkedHashMap<String, Page> getPages() {
    return pages;
  }

  @JsonProperty("allpages")
  public List<Page> getAllPages() {
    return allPages;
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
  public LinkedHashMap<String, BadRevId> getBadRevIds() {
    return badRevIds;
  }
}
