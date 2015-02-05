package com.wikia.mwapi;

import com.wikia.mwapi.decorator.MWApiBase;
import com.wikia.mwapi.enumtypes.FormatEnum;
import com.wikia.mwapi.fluent.query.MainModuleOption;
import com.wikia.mwapi.fluent.WikiaChoose;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;


public class MWApi extends MWApiBase implements MainModuleOption {

  private static final Logger logger = LoggerFactory.getLogger(MWApi.class);
  private static final String DEFAULT_BASEURL = "http://%s/api.php";
  private final HttpClient httpClient;


  protected MWApi() {
    this(HttpClientBuilder.create().build());
  }

  protected MWApi(HttpClient httpClient) {
    this.httpClient = httpClient;
    format(FormatEnum.json);
  }

  public static WikiaChoose createBuilder() {
    return new MWApi();
  }

  public static WikiaChoose createBuilder(HttpClient httpClient) {
    return new MWApi(httpClient);
  }

  @Override
  protected void buildUrl(URIBuilder builder) {
    builder.addParameter("action", getAction().toString());
    builder.addParameter("format", getFormat().toString());
  }

  @Override
  protected URIBuilder createURIBuilder() throws URISyntaxException {
    return new URIBuilder(String.format(DEFAULT_BASEURL, getDomain()));
  }

  protected InputStream handleMWRequest(String url) throws IOException {
    HttpUriRequest request = new HttpGet(url);
    HttpResponse response = httpClient.execute(request);
    return response.getEntity().getContent();
  }
}
