package com.wikia.mwapi.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {

  private Query query;

  private QueryContinue queryContinue;

  private Warning warnings;

  private String servedby;

  private MWError error;


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

  public String getServedby() {
    return servedby;
  }

  public MWError getError() {
    return error;
  }
}
