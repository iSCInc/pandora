package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryStat {

  private String categoryName;
  private int size;
  private int pages;
  private int files;
  private int subcats;

  @JsonProperty("*")
  public String getCategoryName() {
    return categoryName;
  }

  public int getSize() {
    return size;
  }

  public int getPages() {
    return pages;
  }

  public int getFiles() {
    return files;
  }

  public int getSubcats() {
    return subcats;
  }
}
