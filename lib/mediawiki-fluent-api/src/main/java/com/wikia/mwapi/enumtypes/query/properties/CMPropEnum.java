package com.wikia.mwapi.enumtypes.query.properties;

public enum CMPropEnum {
  IDS,
  TITLE,
  SORTKEY,
  SORTKEYPREFIX,
  TYPE,
  TIMESTAMP;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
