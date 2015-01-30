package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Page {

  private int pageId;
  private int ns;
  private String title;
  private String missing;
  private String anonContributors;
  private List<Category> categories;
  private List<User> contributors;
  private List<Image> images;

  private ArrayList<Revision> revisions;

  public ArrayList<Revision> getRevisions() {
    return revisions;
  }

  @JsonProperty("pageid")
  public int getPageId() {
    return pageId;
  }

  public int getNs() {
    return ns;
  }


  public String getTitle() {
    return title;
  }

  public String getMissing() {
    return missing;
  }

  public Revision getFirstRevision() {
    Revision revision = null;
    if (getRevisions() != null && getRevisions().size() > 0) {
      revision = getRevisions().get(0);
    }
    return revision;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public List<Image> getImages() {
    return images;
  }

  @JsonProperty("contributors")
  public List<User> getContributors() {
    return contributors;
  }

  @JsonProperty("anoncontributors")
  public String getAnonContributors() {
    return anonContributors;
  }
}
