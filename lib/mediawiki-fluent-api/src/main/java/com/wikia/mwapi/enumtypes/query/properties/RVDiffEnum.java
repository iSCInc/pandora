package com.wikia.mwapi.enumtypes.query.properties;

public enum RVDiffEnum {
  PREV,
  NEXT,
  CUR;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
