package com.wikia.pandora.gateway.mercury;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.Validate;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

///TODO refactor this class
public class MercuryGateway {

  private static final Logger logger = LoggerFactory.getLogger(MercuryGateway.class);

  public static final String DEFAULT_WIKIA_HOST_FORMAT = "%s.wikia.com";

  public static final String
      DEFAULT_MERCURY_COMMENT_REQUEST_FORMAT =
      "http://%s:%d/api/v1/Mercury/ArticleComments?title=%s";

  public static final String
      DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT =
      "http://%s:%d/api/v1/Mercury/Article?title=%s";

  private final HttpClient httpClient;
  private final String host;
  private final Integer port;

  public MercuryGateway(HttpClient httpClient, String host, Integer port) {
    this.httpClient = httpClient;
    this.host = host;
    this.port = port;
  }


  /**
   * Get an article given a wikia and title.
   *
   * FIXME: How do we handle validation of the payload that we received from MediaWiki? Do we apply
   * JSON schema to the payload when we getArticle it? Do we use some form of manual map
   * validation?
   *
   * @return Map<String, Object>
   */
  public ImmutableMap<String, Object> getArticle(String wikia, String title) throws IOException {
    final Map<String, Object> article;
    ObjectMapper mapper = new ObjectMapper();
    Optional<String> response = this.executeHttpRequest(wikia, formatArticleMercuryRequest(title));

    if (response.isPresent()) {
      article = mapper.readValue(response.get(), new TypeReference<HashMap<String, Object>>() {});
    } else {
      throw new IllegalStateException(
          String.format("Error, the response for %s/%s is not valid!", wikia, title));
    }

    return validateMercuryArticleMap(article);
  }

  public ImmutableMap<String, Object> getCommentsForArticle(String wikia, String title)
      throws IOException {
    final Map<String, Object> articleComments;
    ObjectMapper mapper = new ObjectMapper();
    Optional<String> response = this.executeHttpRequest(wikia, formatCommentMercuryRequest(title));
    logger.info(String.valueOf(response));
    if (response.isPresent()) {
      articleComments =
          mapper.readValue(response.get(), new TypeReference<HashMap<String, Object>>() { });
    } else {
      throw new IllegalStateException(
          String.format("Error, the response for %s/%s is not valid!", wikia, title));
    }
    //todo add validation
    return ImmutableMap.copyOf(articleComments);
  }

  public String formatArticleMercuryRequest(String title) {
    return formatMercuryRequest(title, this.DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT);
  }

  public String formatCommentMercuryRequest(String title) {
    return formatMercuryRequest(title, this.DEFAULT_MERCURY_COMMENT_REQUEST_FORMAT);
  }

  public String formatMercuryRequest(String title, String format) {
    try {
      return String.format(format, this.host, this.port,
                           URLEncoder.encode(title, StandardCharsets.UTF_8.toString()));
    } catch (UnsupportedEncodingException e) {
      return String.format(format, title);
    }
  }

  public Optional<String> executeHttpRequest(final String wikia, final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
    httpGet.setHeader(HTTP.TARGET_HOST, String.format(DEFAULT_WIKIA_HOST_FORMAT, wikia));
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    return Optional.of(this.httpClient.execute(httpGet, responseHandler));
  }

  public ImmutableMap<String, Object> validateMercuryArticleMap(Map article) {
    Validate.notEmpty(article, "Empty map!");
    Map<String, Object> dataElement = (Map<String, Object>) article.get("data");
    Validate.notEmpty(dataElement, "Empty \"data\" element");

    Map<String, Object> detailsElement = (Map<String, Object>) dataElement.get("details");
    Validate.notEmpty(detailsElement, "Empty \"details\" element");

    Map<String, Object> articleElement = (Map<String, Object>) dataElement.get("article");
    Validate.notEmpty(articleElement, "Empty \"article\" element");

    return ImmutableMap.copyOf(article);
  }
}
