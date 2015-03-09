package com.wikia.pandora.core.domain.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdsContextMock {

  private final Map<String, Object> context;

  public AdsContextMock() {

    Map<String, Object> opts = new HashMap<>();
    opts.put("adsInContent", true);
    opts.put("adsInHead", true);
    opts.put("enableAdsInMaps", true);
    opts.put("lateAdsAfterPageLoad", true);
    opts.put("pageType", "no_ads");
    opts.put("trackSlotState", true);

    Map<String, Object> targeting = new HashMap<>();

    targeting.put("enableKruxTargeting", true);
    targeting.put("enablePageCategories", true);
    targeting.put("pageArticleId", 115091);
    targeting.put("pageIsArticle", true);
    targeting.put("pageName", "Meg_Guzulescu");
    targeting.put("pageType", "article");
    targeting.put("skin", "mercury");
    targeting.put("wikiCategory", "ent");
    targeting.put("wikiCustomKeyValues",
                  "sex=m;age=under18;age=13-17;age=18-24;age=25-34;age=teen;age=kids;media=tv;gnre=comedy;media=movies;theme=toy");
    targeting.put("wikiDbName", "muppet");
    targeting.put("wikiDirectedAtChildren", true);
    targeting.put("wikiIsTop1000", true);
    targeting.put("wikiLanguage", "en");
    targeting.put("wikiVertical", "Entertainment");

    Map<String, Object> providers = new HashMap<>();

    providers.put("remnantGptMobile", true);

    List<Object> slots = new ArrayList<>();
    List<Object> forceProviders = new ArrayList<>();

    context = new HashMap<>();
    context.put("opts", opts);
    context.put("targeting", targeting);
    context.put("providers", providers);
    context.put("slots", slots);
    context.put("forceProviders", forceProviders);

  }

  public Map<String, Object> getContext() {
    return context;
  }
}
