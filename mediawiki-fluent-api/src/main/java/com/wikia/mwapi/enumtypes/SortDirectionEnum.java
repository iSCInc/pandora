package com.wikia.mwapi.enumtypes;

public enum SortDirectionEnum {
  ASCENDING,
  DESCENDING;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
