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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

///TODO refactor this class
public class MercuryGateway {

  private static final Logger logger = LoggerFactory.getLogger(MercuryGateway.class);

  public static final String DEFAULT_WIKIA_HOST_FORMAT = "%s.wikia.com";

  public static final String
      DEFAULT_MERCURY_COMMENT_REQUEST_PATH =
      "/api/v1/Mercury/ArticleComments";

  public static final String
      DEFAULT_MERCURY_ARTICLE_REQUEST_PATH =
      "/api/v1/Mercury/Article";

  private final HttpClient httpClient;
  private final int port = 80;

  public MercuryGateway(HttpClient httpClient) {
    this.httpClient = httpClient;
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
    URI requestURI;
    final Map<String, Object> article;

    try {
      requestURI = mercuryArticleRequestURI(wikia, title);
    } catch (URISyntaxException e) {
      throw new IllegalStateException(
          String.format("Error, could not generate a valid URI for wikia %s and title %s!",
                        wikia, title));
    }

    article = doMercuryAPIGet(requestURI.toString());

    return validateMercuryArticleMap(article);
  }

  public ImmutableMap<String, Object> getCommentsForArticle(String wikia, String title)
      throws IOException {
    Optional<String> response;
    URI requestURI;
    final Map<String, Object> articleComments;

    try {
      requestURI = mercuryCommentRequestURI(wikia, title);
    } catch (URISyntaxException e) {
      throw new IllegalStateException(
          String.format("Error, could not generate a valid URI for wikia %s and title %s!",
                        wikia, title));
    }

    articleComments = doMercuryAPIGet(requestURI.toString());

    //todo add validation
    return ImmutableMap.copyOf(articleComments);
  }

  public Map<String, Object> doMercuryAPIGet(String requestURL) throws IOException {
    Optional<String> response;
    final Map<String, Object> mercuryData;
    ObjectMapper mapper = new ObjectMapper();

    response = this.executeHttpRequest(requestURL);
    if (response.isPresent()) {
      mercuryData =
          mapper.readValue(response.get(), new TypeReference<HashMap<String, Object>>() {
          });
    } else {
      throw new IllegalStateException(
          String.format("Error, the response for %s is not valid!", requestURL));
    }

    return mercuryData;
  }


  public URI mercuryArticleRequestURI(String wikia, String title) throws URISyntaxException {
    return mercuryRequestURI(wikia,
                             this.DEFAULT_MERCURY_ARTICLE_REQUEST_PATH,
                             String.format("title=%s", title));
  }

  public URI mercuryCommentRequestURI(String wikia, String title) throws URISyntaxException {
    return mercuryRequestURI(wikia,
                             this.DEFAULT_MERCURY_COMMENT_REQUEST_PATH,
                             String.format("title=%s", title));
  }

  public URI mercuryRequestURI(String wikia, String path, String query) throws URISyntaxException {
    return new URI("http",
                   null,
                   String.format(this.DEFAULT_WIKIA_HOST_FORMAT, wikia),
                   this.port,
                   path,
                   query,
                   null);
  }

  public Optional<String> executeHttpRequest(final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
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
