package com.wikia.mwapi.enumtypes.query.properties;

public enum CMTypeEnum {
  PAGE,
  SUBCAT,
  FILE;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
