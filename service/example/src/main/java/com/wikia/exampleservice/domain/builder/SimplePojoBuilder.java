package com.wikia.exampleservice.domain.builder;

import com.wikia.exampleservice.domain.SimplePojo;

///Builder was built by Builder Generator (Plugin in InteliJ)
public class SimplePojoBuilder {

  private int id;
  private String someString;
  private boolean someBool;

  private SimplePojoBuilder() {
  }

  public static SimplePojoBuilder aSimplePojo() {
    return new SimplePojoBuilder();
  }

  public SimplePojoBuilder withId(int id) {
    this.id = id;
    return this;
  }

  public SimplePojoBuilder withSomeString(String someString) {
    this.someString = someString;
    return this;
  }

  public SimplePojoBuilder withSomeBool(boolean someBool) {
    this.someBool = someBool;
    return this;
  }

  public SimplePojoBuilder but() {
    return aSimplePojo().withId(id).withSomeString(someString).withSomeBool(someBool);
  }

  public SimplePojo build() {
    SimplePojo simplePojo = new SimplePojo(id, someString, someBool);
    return simplePojo;
  }
}
