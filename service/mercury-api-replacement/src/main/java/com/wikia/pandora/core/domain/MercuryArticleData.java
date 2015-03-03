package com.wikia.pandora.core.domain;

import com.wikia.pandora.domain.User;

import java.util.List;

public class MercuryArticleData {

  private final MercuryArticleDetail details;
  private final List<User> topContributors;
  private final MercuryArticle article;
  private final List<MercuryRelatedPage> relatedPages;
  private final AdsContext adsContext;

  public MercuryArticleData(MercuryArticleDetail details, List<User> topContributors,
                            MercuryArticle article, List<MercuryRelatedPage> relatedPages,
                            AdsContext adsContext) {
    this.details = details;
    this.topContributors = topContributors;
    this.article = article;
    this.relatedPages = relatedPages;
    this.adsContext = adsContext;
  }

  public MercuryArticleDetail getDetails() {
    return details;
  }

  public List<User> getTopContributors() {
    return topContributors;
  }

  public MercuryArticle getArticle() {
    return article;
  }

  public List<MercuryRelatedPage> getRelatedPages() {
    return relatedPages;
  }

  public AdsContext getAdsContext() {
    return adsContext;
  }


}
