package com.wikia.mwapi;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ApiResponse {

  private Query query;

  private QueryContinue queryContinue;


  public ApiResponse() {
  }

  public Query getQuery() {
    return query;
  }

  @JsonProperty("query-continue")
  public QueryContinue getQueryContinue() {
    return queryContinue;
  }

  public static class Query {

    private ArrayList<Normalized> normalized;

    private LinkedHashMap<String, Page> pages;

    public ArrayList<Normalized> getNormalized() {
      return normalized;
    }

    public LinkedHashMap<String, Page> getPages() {
      return pages;
    }

    public Page getFirstPage() {
      Page page = null;
      if (getPages() != null && getPages().entrySet().size() > 0) {
        page = getPages().entrySet().iterator().next().getValue();
      }
      return page;
    }
  }


  public static class Normalized {

    private String from;
    private String to;


    public String getFrom() {
      return from;
    }

    public String getTo() {
      return to;
    }
  }

  public static class Page {

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

    public static class Revision {


      private String content;

      @JsonProperty("*")
      public String getContent() {
        return content;
      }
    }
  }


  public class QueryContinue {

    private Revisions revisions;

    public Revisions getRevisions() {
      return revisions;
    }

    public class Revisions {

      private int rvStartId;

      @JsonProperty("rvstartid")
      public int getRvStartId() {
        return rvStartId;
      }
    }
  }
}
