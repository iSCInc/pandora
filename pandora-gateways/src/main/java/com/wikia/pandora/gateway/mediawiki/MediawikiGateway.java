package com.wikia.pandora.gateway.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.MWApi;
import com.wikia.mwapi.fluent.WikiaChoose;

import org.apache.http.client.HttpClient;

public class MediawikiGateway {

  private final HttpClient httpClient;

  public MediawikiGateway(HttpClient httpClient) {

    this.httpClient = httpClient;
  }

  public WikiaChoose queryBuilder() {
    return MWApi.createBuilder(httpClient);
  }

  public ApiResponse getArticleByTitle(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleWithContentByTitle(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .prop().revisions()
        .rv().rvLimit(1)
        .rv().rvContent()
        .get();

    return apiResponse;
  }

  //TODO add filter
  public ApiResponse getCategoryByName(String wikia, String categoryName) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(categoryName)
        .get();
    return apiResponse;
  }

  public ApiResponse getArticlesFromWikia(String wikia) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .allPages()
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleCategories(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .prop().categories()
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleImages(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction().titles(title)
        .prop().images()
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleRevisions(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .prop().revisions()
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleContributors(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .prop().contributors()
        .get();
    return apiResponse;
  }
}