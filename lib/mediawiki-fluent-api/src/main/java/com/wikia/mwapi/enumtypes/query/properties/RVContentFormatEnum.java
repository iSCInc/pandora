package com.wikia.mwapi.enumtypes.query.properties;

public enum RVContentFormatEnum {
  TEXT_X_WIKI,
  TEXT_JAVASCRIPT,
  APPLICATION_JSON,
  TEXT_CSS,
  TEXT_PLAIN;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
