package com.wikia.mwapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.fluent.ActionChoose;
import com.wikia.mwapi.fluent.OptionChoose;
import com.wikia.mwapi.fluent.TitlesChoose;
import com.wikia.mwapi.fluent.WikiaChoose;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MWApi implements WikiaChoose, ActionChoose, TitlesChoose, OptionChoose {

  private static final Logger logger = LoggerFactory.getLogger(MWApi.class);

  public static final String QUERY = "query";
  public static final String REVISIONS = "revisions";
  public static final String CONTENT = "content";
  public static final String ALLPAGES = "allpages";
  public static final String DEFAULT_BASEURL = "http://%s.wikia.com/api.php";

  private String wikia;
  private String query;
  private String[] titles;
  private String format;
  private final List<String> prop;

  private HttpClient httpClient;
  private Integer rvLimit;
  private String rvProp;
  private String list;


  protected MWApi() {
    this(HttpClientBuilder.create().build());
  }

  public MWApi(HttpClient httpClient) {
    this.httpClient = httpClient;
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
  public OptionChoose allPages() {
    this.list = ALLPAGES;
    return this;
  }

  @Override
  public String url() {
    return buildUrl();
  }

  @Override
  public ApiResponse get() {
    ApiResponse apiResponse = null;
    ObjectMapper mapper = new ObjectMapper();
    String url = buildUrl();

    try {
      InputStream response = handleMWRequest(url);
      apiResponse = mapper.readValue(response, ApiResponse.class);
    } catch (IOException e) {
      logger.debug(e.getMessage(), e);
    }

    return apiResponse;
  }

  public InputStream handleMWRequest(String url) throws IOException {
    HttpUriRequest request = new HttpGet(url);
    HttpResponse response = httpClient.execute(request);
    return response.getEntity().getContent();
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

  public String buildUrl() {
    URIBuilder b = null;
    try {
      b = new URIBuilder(String.format(DEFAULT_BASEURL, wikia));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    List<String> params = new ArrayList<String>();
    if (query != null) {
      params.add(String.format("action=%s", query));
      b.addParameter("action", query);
    }
    if (titles != null) {
      String joinTitles = String.join("|", titles);
      params.add(String.format("titles=%s", joinTitles));
      b.addParameter("titles", joinTitles);
    }

    if (format != null) {
      params.add(String.format("format=%s", format));
      b.addParameter("format", format);
    }

    if (prop != null && prop.size() > 0) {
      String joinProp = String.join("|", prop);
      params.add(String.format("prop=%s", joinProp));
      b.addParameter("prop", joinProp);
    }

    if (rvLimit != null) {
      params.add(String.format("rvlimit=%s", rvLimit));
      b.addParameter("rvlimit", String.valueOf(rvLimit));
    }

    if (rvProp != null) {
      params.add(String.format("rvprop=%s", rvProp));
      b.addParameter("rvprop", rvProp);
    }

    if (list != null) {
      params.add(String.format("list=%s", list));
      b.addParameter("list", list);
    }

    String url = null;
    try {
      url = b.build().toASCIIString();
    } catch (URISyntaxException e) {
      url = String.format(DEFAULT_BASEURL + "?%s", wikia, String.join("&", params));
    }

    logger.debug(MarkerFactory.getMarker("MWApi url"), url);
    return url;
  }
}
