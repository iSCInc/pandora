package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Page {

  private int pageId;
  private int ns;
  private String title;
  private String missing;

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
}
