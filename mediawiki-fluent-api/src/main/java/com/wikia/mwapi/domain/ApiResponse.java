package com.wikia.mwapi.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {

  private Query query;

  private QueryContinue queryContinue;

  private Warning warnings;


  public ApiResponse() {
  }

  public Query getQuery() {
    return query;
  }

  @JsonProperty("query-continue")
  public QueryContinue getQueryContinue() {
    return queryContinue;
  }

  public Warning getWarnings() {
    return warnings;
  }
}
