package com.wikia.mwapi.decorator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.mwapi.decorator.query.MWApiQuery;
import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.enumtypes.ActionEnum;
import com.wikia.mwapi.enumtypes.FormatEnum;
import com.wikia.mwapi.fluent.query.MainModuleOption;
import com.wikia.mwapi.fluent.query.MethodOption;
import com.wikia.mwapi.fluent.TitlesOrListChoose;
import com.wikia.mwapi.fluent.WikiaChoose;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;

public abstract class MWApiBase implements WikiaChoose, MainModuleOption, MethodOption {

  Logger logger = LoggerFactory.getLogger(MWApiBase.class);
  private String wikia;
  private ActionEnum action;
  private FormatEnum format;
  private String domain;

  public String getDomain() {
    if (domain != null && !Objects.equals(domain, "")) {
      return domain;
    } else {
      return String.format("%s.wikia.com", wikia);
    }
  }


  public ActionEnum getAction() {
    return action;
  }

  public FormatEnum getFormat() {
    return format;
  }

  @Override
  public MainModuleOption wikia(String wikia) {
    this.wikia = wikia;
    return this;
  }

  @Override
  public MainModuleOption domain(String domain) {
    this.domain = domain;
    return this;

  }

  @Override
  public TitlesOrListChoose queryAction() {
    this.action = ActionEnum.QUERY;
    return new MWApiQuery(this);
  }


  public MainModuleOption format(FormatEnum format) {
    this.format = format;
    return this;
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
  public ApiResponse post() {
    throw new NotImplementedException("");
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

  protected String buildUrl() throws URISyntaxException {
    URIBuilder builder = createURIBuilder();
    buildUrl(builder);
    String url = builder.build().toASCIIString();
    logger.debug(MarkerFactory.getMarker("MWApi url"), url);
    return url;
  }

  protected abstract void buildUrl(URIBuilder builder);

  protected abstract URIBuilder createURIBuilder() throws URISyntaxException;


  protected abstract InputStream handleMWRequest(String url) throws IOException;
}
