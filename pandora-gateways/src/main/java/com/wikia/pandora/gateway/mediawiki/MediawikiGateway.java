package com.wikia.pandora.gateway.mediawiki;

import com.google.common.collect.Lists;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.MWApi;
import com.wikia.mwapi.enumtypes.query.properties.RVPropEnum;
import com.wikia.mwapi.fluent.WikiaChoose;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MediawikiGateway {

  public static final String CATEGORY = "category";
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
        .revisions()
        .rvlimit(1)
        .rvprop(RVPropEnum.content)
        .get();

    return apiResponse;
  }

  //TODO add filter
  public ApiResponse getCategoryByName(String wikia, String categoryName) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(String.format("%s:%s", CATEGORY, categoryName))
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
        .categories()
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleImages(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction().titles(title)
        .images()
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleRevisions(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .revisions()
        .rvlimit(10)
        .get();
    return apiResponse;
  }

  public ApiResponse getArticleContributors(String wikia, String title) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .titles(title)
        .contributors()
        .get();
    return apiResponse;
  }

  public ApiResponse getCategoryArticles(String wikia, String categoryName, int limit,
                                         String offset) {

    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .categorymembers()
        .cmtitle(String.format("%s:%s", CATEGORY, categoryName))
        .cmcontinue(offset)
        .cmlimit(limit)
        .get();
    return apiResponse;
  }

  public ApiResponse getCategoriesFromWikia(String wikia, int limit, String offset) {
    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .allcategories()
        .aclimit(limit)
        .accontinue(offset)
        .get();
    return apiResponse;
  }

  public ApiResponse getRevisionById(String wikia, Long revId) {
    return getRevision(wikia, revId, false);
  }

  public ApiResponse getRevisionByIdWithContent(String wikia, Long revId) {
    return getRevision(wikia, revId, true);
  }

  private ApiResponse getRevision(String wikia, Long revId, boolean withContent) {
    List<RVPropEnum> rvPropEnumList = Arrays.asList(RVPropEnum.user,
                                                    RVPropEnum.ids,
                                                    RVPropEnum.comment,
                                                    RVPropEnum.timestamp);
    if (withContent) {
      rvPropEnumList.add(RVPropEnum.content);
    }

    ApiResponse apiResponse = queryBuilder()
        .wikia(wikia)
        .queryAction()
        .revIds(revId)
        .info()
        .revisions()
        .rvprop((RVPropEnum[]) rvPropEnumList.toArray())
        .get();

    return apiResponse;
  }
}
