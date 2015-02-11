package com.wikia.mwapi.decorator.query.list;

import com.wikia.mwapi.decorator.query.MWApiQueryBase;
import com.wikia.mwapi.decorator.query.MWApiQueryDecorator;
import com.wikia.mwapi.enumtypes.SortDirectionEnum;
import com.wikia.mwapi.enumtypes.query.properties.ACPropEnum;
import com.wikia.mwapi.fluent.query.AllCategoriesOption;
import com.wikia.mwapi.util.URIBuilderHelper;

import org.apache.http.client.utils.URIBuilder;


public class MWApiAllCategories extends MWApiQueryDecorator implements AllCategoriesOption {

  private String acFrom;
  private String acTo;
  private String acContinue;
  private String acPrefix;
  private SortDirectionEnum acDir;
  private int acMin;
  private int acMax;
  private int acLimit;
  private ACPropEnum[] prop;


  public MWApiAllCategories(MWApiQueryBase parent) {
    super(parent);
  }

  @Override
  public void buildUrl(URIBuilder builder) {
    super.buildUrl(builder);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "acfrom", acFrom);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "acto", acTo);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "accontinue", acContinue);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "acprefix", acPrefix);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "acdir", acDir);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "acmin", acMin);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "acmax", acMax);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "aclimit", acLimit);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "acprop", prop);
  }

  @Override
  public AllCategoriesOption acfrom(String acFrom) {
    this.acFrom = acFrom;
    return this;
  }

  @Override
  public AllCategoriesOption acto(String acTo) {
    this.acTo = acTo;
    return this;
  }

  @Override
  public AllCategoriesOption accontinue(String acContinue) {
    this.acContinue = acContinue;
    return this;
  }

  @Override
  public AllCategoriesOption acprefix(String acPrefix) {
    this.acPrefix = acPrefix;
    return this;
  }

  @Override
  public AllCategoriesOption acdir(SortDirectionEnum acDir) {
    this.acDir = acDir;
    return this;
  }

  @Override
  public AllCategoriesOption acmin(int minArticles) {
    acMin = minArticles;
    return this;
  }

  @Override
  public AllCategoriesOption acmax(int maxArticles) {
    this.acMax = maxArticles;
    return this;
  }

  @Override
  public AllCategoriesOption aclimit(int acLimit) {
    this.acLimit = acLimit;
    return this;
  }

  @Override
  public AllCategoriesOption acprop(ACPropEnum... prop) {
    this.prop = prop;
    return this;
  }
}
