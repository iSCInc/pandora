package com.wikia.mwapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mwapi.fluent.ActionChoose;
import com.wikia.mwapi.fluent.OptionChoose;
import com.wikia.mwapi.fluent.TitlesChoose;
import com.wikia.mwapi.fluent.WikiaChoose;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MWApi implements WikiaChoose, ActionChoose, TitlesChoose, OptionChoose {

  private static final Logger logger = LoggerFactory.getLogger(MWApi.class);

  public static final String QUERY = "query";
  public static final String REVISIONS = "revisions";
  public static final String CONTENT = "content";
  private String baseUrl;

  private String wikia;
  private String query;
  private String[] titles;
  private String format;
  private final List<String> prop;

  private HttpClient httpClient;
  private Integer rvLimit;
  private String rvProp;


  protected MWApi() {
    this(HttpClientBuilder.create().build());
  }

  public MWApi(HttpClient httpClient) {
    this.httpClient = httpClient;
    baseUrl = "http://%s.wikia.com/api.php?%s";
    format = "json";
    prop = new ArrayList<String>();
  }

  public static WikiaChoose createBuilder() {
    return new MWApi();
  }

  public static WikiaChoose createBuilder(HttpClient httpClient) {
    return new MWApi(httpClient);
  }

  @Override
  public ActionChoose wikia(String wikia) {
    this.wikia = wikia;
    return this;
  }

  @Override
  public TitlesChoose queryAction() {
    this.query = QUERY;
    return this;
  }

  @Override
  public OptionChoose titles(String... titles) {
    this.titles = titles;
    return this;
  }

  @Override
  public ApiResponse get() {
    ApiResponse apiResponse = null;
    String url = buildUrl();
    String result = null;
    HttpUriRequest request = new HttpGet(url);
    try {
      HttpResponse response = httpClient.execute(request);
      ObjectMapper mapper = new ObjectMapper();
      apiResponse =
          mapper.readValue(response.getEntity().getContent(), ApiResponse.class);
    } catch (IOException e) {
      logger.debug(e.getMessage(), e);
    }
    return apiResponse;
  }

  @Override
  public OptionChoose revisions() {
    prop.add(REVISIONS);
    return this;
  }

  @Override
  public OptionChoose rvLimit(Integer limit) {
    rvLimit = limit;
    return this;
  }

  @Override
  public OptionChoose rvContent() {
    rvProp = CONTENT;
    return this;
  }

  private String buildUrl() {

    List<String> params = new ArrayList<String>();
    if (query != null) {
      params.add(String.format("action=%s", query));
    }
    if (titles != null) {
      String joinTitles = String.join("|", titles);
      params.add(String.format("titles=%s", joinTitles));
    }

    if (format != null) {
      params.add(String.format("format=%s", format));
    }

    if (prop != null) {
      String joinProp = String.join("|", prop);
      params.add(String.format("prop=%s", joinProp));
    }

    if (rvLimit != null) {
      params.add(String.format("rvlimit=%s", rvLimit));
    }

    if (rvProp != null) {
      params.add(String.format("rvprop=%s", rvProp));
    }

    String url = String.format(baseUrl, wikia, String.join("&", params));
    logger.debug(MarkerFactory.getMarker("MWApi url"), url);
    return url;
  }
}
