package com.wikia.exampleservice.domain;

public class SimplePojo {

  private final int id;
  private final String someString;
  private final boolean someBool;


  public SimplePojo(int id, String someString, boolean someBool) {
    this.id = id;
    this.someString = someString;
    this.someBool = someBool;
  }

  public int getId() {
    return id;
  }

  public String getSomeString() {
    return someString;
  }

  public boolean isSomeBool() {
    return someBool;
  }
}
