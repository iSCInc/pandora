package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.enumtypes.SortDirectionEnum;
import com.wikia.mwapi.enumtypes.query.properties.CLPropEnum;
import com.wikia.mwapi.enumtypes.query.properties.CLShowEnum;
import com.wikia.mwapi.fluent.query.CategoriesOption;
import com.wikia.mwapi.util.URIBuilderHelper;

import org.apache.http.client.utils.URIBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MWApiCategories extends MWApiQueryDecorator implements CategoriesOption {

  private List<CLPropEnum> clPropEnumList;
  private List<CLShowEnum> clShowEnums;
  private int clLimit;
  private String clContinue;
  private List<String> clCategories;
  private SortDirectionEnum direction;


  public MWApiCategories(MWApiQuery parent) {
    super(parent);
    clPropEnumList = new ArrayList<>();
    clShowEnums = new ArrayList<>();
    clCategories = new ArrayList<>();
  }

  @Override
  public CategoriesOption clprop(CLPropEnum... clPropEnums) {
    Collections.addAll(this.clPropEnumList, clPropEnums);
    return this;
  }

  @Override
  public CategoriesOption clshow(CLShowEnum... clShowEnums) {
    Collections.addAll(this.clShowEnums, clShowEnums);
    return this;
  }

  @Override
  public CategoriesOption cllimit(int limit) {
    this.clLimit = limit;
    return this;
  }

  @Override
  public CategoriesOption clcontinue(String clContinue) {
    this.clContinue = clContinue;
    return this;
  }

  @Override
  public CategoriesOption clcategories(String... categoryfilter) {
    Collections.addAll(this.clCategories, categoryfilter);
    return this;
  }

  @Override
  public CategoriesOption cldir(SortDirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  @Override
  public void buildUrl(URIBuilder builder) {
    URIBuilderHelper.addParameter(builder, "clprop", clPropEnumList);
    URIBuilderHelper.addParameter(builder, "clshow", clShowEnums);
    if (clLimit > 0) {
      builder.addParameter("cllimit", String.valueOf(clLimit));
    }
    if (clContinue != null) {
      builder.addParameter("clcontinue", clContinue);
    }
    URIBuilderHelper.addParameter(builder, "clcategories", clCategories);
    if (direction != null) {
      builder.addParameter("cldir", direction.toString());
    }
  }
}
