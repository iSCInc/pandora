package com.wikia.mwapi.enumtypes;

public enum SortDirectionByTimeEnum {
  NEWER,
  OLDER;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
