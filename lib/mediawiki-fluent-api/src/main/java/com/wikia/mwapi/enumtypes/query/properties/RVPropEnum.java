package com.wikia.mwapi.enumtypes.query.properties;

public enum RVPropEnum {
  IDS,
  FLAGS,
  TIMESTAMP,
  USER,
  USERID,
  SIZE,
  SHA1,
  CONTENTMODEL,
  COMMENT,
  PARSEDCOMMENT,
  CONTENT,
  TAGS,
  FLAGGED;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
