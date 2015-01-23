package com.wikia.mwapi;


import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ApiResponse {

  private Query query;

  public ApiResponse() {
  }

  public Query getQuery() {
    return query;
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
      if (getPages() != null && getPages().entrySet().size() == 1) {
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

    private int pageid;
    private int ns;
    private String title;

    public int getPageid() {
      return pageid;
    }

    public int getNs() {
      return ns;
    }

    public String getTitle() {
      return title;
    }
  }
}
