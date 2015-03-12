package com.wikia.userpreference.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserPreference {

  @JsonProperty
  String name;

  @JsonProperty
  String value;

  private UserPreference(Builder builder) {
    name = builder.name;
    value = builder.value;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {

    private String name;
    private String value;

    private Builder() {
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder value(String value) {
      this.value = value;
      return this;
    }

    public UserPreference build() {
      return new UserPreference(this);
    }
  }
}
