package com.wikia.pandora.core.domains.builder;

import com.wikia.pandora.core.domains.AdsContext;
import com.wikia.pandora.core.domains.MercuryArticle;
import com.wikia.pandora.core.domains.MercuryArticleData;
import com.wikia.pandora.core.domains.MercuryArticleDetail;
import com.wikia.pandora.core.domains.MercuryRelatedPage;
import com.wikia.pandora.core.domains.User;

import java.util.List;


public class MercuryArticleDataBuilder {

  private MercuryArticleDetail details;
  private List<User> topContributors;
  private MercuryArticle article;
  private List<MercuryRelatedPage> relatedPages;
  private AdsContext adsContext;

  private MercuryArticleDataBuilder() {
  }

  public static MercuryArticleDataBuilder aMercuryArticleData() {
    return new MercuryArticleDataBuilder();
  }

  public MercuryArticleDataBuilder withDetails(MercuryArticleDetail details) {
    this.details = details;
    return this;
  }

  public MercuryArticleDataBuilder withTopContributors(List<User> topContributors) {
    this.topContributors = topContributors;
    return this;
  }

  public MercuryArticleDataBuilder withArticle(MercuryArticle article) {
    this.article = article;
    return this;
  }

  public MercuryArticleDataBuilder withRelatedPages(List<MercuryRelatedPage> relatedPages) {
    this.relatedPages = relatedPages;
    return this;
  }

  public MercuryArticleDataBuilder withAdsContext(AdsContext adsContext) {
    this.adsContext = adsContext;
    return this;
  }

  public MercuryArticleDataBuilder but() {
    return aMercuryArticleData().withDetails(details).withTopContributors(topContributors)
        .withArticle(article).withRelatedPages(relatedPages).withAdsContext(adsContext);
  }

  public MercuryArticleData build() {
    MercuryArticleData
        mercuryArticleData =
        new MercuryArticleData(details, topContributors, article, relatedPages, adsContext);
    return mercuryArticleData;
  }
}
