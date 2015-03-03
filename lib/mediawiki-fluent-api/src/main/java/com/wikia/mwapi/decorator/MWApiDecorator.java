package com.wikia.mwapi.decorator;

import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class MWApiDecorator extends MWApiBase {

  private final MWApiBase parent;

  public MWApiDecorator(MWApiBase parent) {
    this.parent = parent;
  }

  @Override
  protected void buildUrl(URIBuilder builder) {
    parent.buildUrl(builder);
  }

  @Override
  protected URIBuilder createURIBuilder() throws URISyntaxException {
    return parent.createURIBuilder();
  }

  @Override
  protected InputStream handleMWRequest(String url) throws IOException {
    return parent.handleMWRequest(url);
  }

}
