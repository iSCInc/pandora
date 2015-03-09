package com.wikia.mwapi.enumtypes;

public enum FormatEnum {
  DBG,
  DBGFM,
  DUMP,
  DUMPFM,
  JSON,
  JSONFM,
  NONE,
  PHP,
  PHPFM,
  RAWFM,
  TXT,
  TXTFM,
  WDDX,
  WDDXFM,
  XML,
  XMLFM,
  YAML,
  YAMLFM;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
