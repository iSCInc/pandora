package com.wikia.pandora.gateway.mercury;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

///TODO refactor this class
public class MercuryGateway {

  private static final Logger logger = LoggerFactory.getLogger(MercuryGateway.class);

  public static final String
      DEFAULT_MERCURY_COMMENT_REQUEST_FORMAT =
      "http://%s.wikia.com/api/v1/Mercury/ArticleComments?title=%s";

  public static final String
      DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT =
      "http://%s.wikia.com/api/v1/Mercury/Article?title=%s";

  private final HttpClient httpClient;
  private final String articleRequestFormat;
  private final String commentsRequestFormat;

  public MercuryGateway(HttpClient httpClient, String articleRequestFormat,
                        String commentsRequestFormat) {
    this.httpClient = httpClient;
    this.articleRequestFormat = articleRequestFormat;
    this.commentsRequestFormat = commentsRequestFormat;
  }

  public MercuryGateway(HttpClient httpClient) {
    this.httpClient = httpClient;
    this.articleRequestFormat = MercuryGateway.DEFAULT_MERCURY_ARTICLE_REQUEST_FORMAT;
    this.commentsRequestFormat = MercuryGateway.DEFAULT_MERCURY_COMMENT_REQUEST_FORMAT;
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
    Optional<String> response = this.getRequestHandler(formatArticleMercuryRequest(wikia, title));

    if (response.isPresent()) {
      article = mapper.readValue(response.get(), new TypeReference<HashMap<String, Object>>() {
      });
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
    Optional<String> response = this.getRequestHandler(formatCommentMercuryRequest(wikia, title));
    logger.info(String.valueOf(response));
    if (response.isPresent()) {
      articleComments =
          mapper.readValue(response.get(), new TypeReference<HashMap<String, Object>>() {
          });
    } else {
      throw new IllegalStateException(
          String.format("Error, the response for %s/%s is not valid!", wikia, title));
    }
    //todo add validation
    return ImmutableMap.copyOf(articleComments);
  }

  public String formatArticleMercuryRequest(String wikia, String title) {
    return formatMercuryRequest(wikia, title, this.articleRequestFormat);
  }

  public String formatCommentMercuryRequest(String wikia, String title) {
    return formatMercuryRequest(wikia, title, this.commentsRequestFormat);
  }

  public String formatMercuryRequest(String wikia, String title, String format) {
    try {
      return String.format(format, wikia, URLEncoder.encode(title, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      return String.format(format, wikia, title);
    }
  }

  public Optional<String> getRequestHandler(final String requestUrl) throws IOException {
    HttpGet httpGet = new HttpGet(requestUrl);
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

      public String handleResponse(final HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status == 200) {
          Optional<HttpEntity> entity = Optional.of(response.getEntity());
          if (!entity.isPresent()) {
            throw new ClientProtocolException(
                String.format("Empty response body from '%s'!", requestUrl));
          }
          return EntityUtils.toString(entity.get());
        } else {
          throw new ClientProtocolException(
              String.format("Unexpected response status %d from '%s'.", status, requestUrl));
        }
      }

    };

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
