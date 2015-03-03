package com.wikia.mwapi.enumtypes.query.properties;

public enum CLPropEnum {
  SORTKEY,
  TIMESTAMP,
  HIDDEN;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
