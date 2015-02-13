package com.wikia.mwapi.enumtypes;

public enum SortTypeEnum {
  SORTKEY,
  TIMESTAMP;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
