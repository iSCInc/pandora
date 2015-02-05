package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.enumtypes.SortTypeEnum;
import com.wikia.mwapi.enumtypes.query.properties.CMPropEnum;
import com.wikia.mwapi.enumtypes.query.properties.CMTypeEnum;
import com.wikia.mwapi.fluent.query.CategoryMembersOption;
import com.wikia.mwapi.util.URIBuilderHelper;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.client.utils.URIBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MWApiCategoryMembers extends MWApiQueryDecorator implements CategoryMembersOption {

  private String cmTitle;
  private Long cmPageId;
  private CMPropEnum[] cmPropEnums;
  private int[] namespaces;
  private CMTypeEnum[] cmTypeEnums;
  private String cmContinue;
  private int limit;
  private SortTypeEnum sortTypeEnum;


  public MWApiCategoryMembers(MWApiQueryBase parent) {
    super(parent);

  }

  @Override
  public void buildUrl(URIBuilder builder) {
    super.buildUrl(builder);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmtitle", cmTitle);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmpageid", cmPageId);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmprop", cmPropEnums);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmnamespace", namespaces);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmtype", cmTypeEnums);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmcontinue", cmContinue);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmlimit", limit);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "cmsort", sortTypeEnum);
  }

  @Override
  public CategoryMembersOption cmtitle(String title) {
    this.cmTitle = title;
    return this;
  }

  @Override
  public CategoryMembersOption cmpageid(Long id) {
    cmPageId = id;
    return this;
  }

  @Override
  public CategoryMembersOption cmprop(CMPropEnum... cmPropEnums) {
    this.cmPropEnums = cmPropEnums;
    return this;
  }

  @Override
  public CategoryMembersOption cmnamespace(int... namespaces) {
    this.namespaces = namespaces;
    return this;
  }

  @Override
  public CategoryMembersOption cmtype(CMTypeEnum... cmTypeEnums) {
    this.cmTypeEnums = cmTypeEnums;
    return this;
  }

  @Override
  public CategoryMembersOption cmcontinue(String cmContinue) {
    this.cmContinue = cmContinue;
    return this;
  }

  @Override
  public CategoryMembersOption cmlimit(int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public CategoryMembersOption cmsort(SortTypeEnum sortTypeEnum) {
    this.sortTypeEnum = sortTypeEnum;
    return this;
  }
}
