package com.wikia.mwapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryContinue {

  private Revisions revisions;

  private AllPages allpages;

  private AllCategories allCategories;

  private CategoryMembers categorymembers;

  public Revisions getRevisions() {
    return revisions;
  }

  @JsonProperty("allpages")
  public AllPages getAllpages() {
    return allpages;
  }

  @JsonProperty("categorymembers")
  public CategoryMembers getCategorymembers() {
    return categorymembers;
  }

  @JsonProperty("allcategories")
  public AllCategories getAllCategories() {
    return allCategories;
  }

  public class Revisions {

    private int rvStartId;

    @JsonProperty("rvstartid")
    public int getRvStartId() {
      return rvStartId;
    }
  }

  public class AllPages {

    private String apfrom;

    public String getApfrom() {
      return apfrom;
    }
  }

  public class CategoryMembers {

    private String cmcontinue;


    public String getCmcontinue() {
      return cmcontinue;
    }
  }

  public class AllCategories {

    private String acfrom;

    public String getAcfrom() {
      return acfrom;
    }
  }
}
