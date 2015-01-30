package com.wikia.mwapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.fluent.ActionChoose;
import com.wikia.mwapi.fluent.OptionChoose;
import com.wikia.mwapi.fluent.PropChoose;
import com.wikia.mwapi.fluent.RevisionOptionChoose;
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


public class MWApi implements WikiaChoose, ActionChoose, TitlesChoose, OptionChoose, PropChoose,
                              RevisionOptionChoose {

  private static final Logger logger = LoggerFactory.getLogger(MWApi.class);

  public static final String QUERY = "query";
  public static final String REVISIONS = "revisions";
  public static final String CONTENT = "content";
  public static final String ALLPAGES = "allpages";
  public static final String CATEGORIES = "categories";
  public static final String IMAGES = "images";
  public static final String CONTRIBUTORS = "contributors";
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


  @Override
  public PropChoose prop() {
    return this;
  }

  @Override
  public RevisionOptionChoose rv() {
    return this;
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
    try {
      return buildUrl();
    } catch (URISyntaxException e) {
      logger.debug(e.getMessage(), e);
      return "";
    }
  }

  @Override
  public ApiResponse get() {
    ApiResponse apiResponse = null;
    ObjectMapper mapper = new ObjectMapper();

    try {
      String url = buildUrl();
      InputStream response = handleMWRequest(url);
      apiResponse = mapper.readValue(response, ApiResponse.class);
    } catch (IOException | URISyntaxException e) {
      logger.debug(e.getMessage(), e);
    }

    return apiResponse;
  }

  @Override
  public OptionChoose categories() {
    prop.add(CATEGORIES);
    return this;
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
  public OptionChoose images() {
    prop.add(IMAGES);
    return this;
  }

  /// MediaWiki 1.23
  @Override
  public OptionChoose contributors() {
    prop.add(CONTRIBUTORS);
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

  public String buildUrl() throws URISyntaxException {

    try {
      URIBuilder b = new URIBuilder(String.format(DEFAULT_BASEURL, wikia));

      List<String> params = new ArrayList<String>();
      if (query != null) {
        b.addParameter("action", query);
      }

      if (titles != null) {
        String joinTitles = String.join("|", titles);
        b.addParameter("titles", joinTitles);
      }

      if (format != null) {
        b.addParameter("format", format);
      }

      if (prop != null && prop.size() > 0) {
        String joinProp = String.join("|", prop);
        b.addParameter("prop", joinProp);
      }

      if (rvLimit != null) {
        b.addParameter("rvlimit", String.valueOf(rvLimit));
      }

      if (rvProp != null) {
        b.addParameter("rvprop", rvProp);
      }

      if (list != null) {
        b.addParameter("list", list);
      }

      String url = b.build().toASCIIString();
      logger.debug(MarkerFactory.getMarker("MWApi url"), url);
      return url;
    } catch (URISyntaxException e) {
      throw e;
    }
  }
}
