package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.enumtypes.SortDirectionByTimeEnum;
import com.wikia.mwapi.enumtypes.query.properties.RVContentFormatEnum;
import com.wikia.mwapi.enumtypes.query.properties.RVDiffEnum;
import com.wikia.mwapi.enumtypes.query.properties.RVPropEnum;
import com.wikia.mwapi.fluent.query.RevisionsOption;
import com.wikia.mwapi.util.URIBuilderHelper;

import org.apache.http.client.utils.URIBuilder;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MWApiRevisions extends MWApiQueryDecorator implements RevisionsOption {

  private List<RVPropEnum> rvPropsEnums;
  private int rvLimit;
  private boolean expandTemplates;
  private boolean generateXml;
  private boolean parseRevision;
  private int section;
  private RVDiffEnum diffTo;
  private boolean diffToText;
  private RVContentFormatEnum contentFormat;
  private int startId;
  private int endId;
  private DateTime rvStart;
  private DateTime rvEnd;
  private SortDirectionByTimeEnum sortDirection;
  private String user;
  private String excludeUser;
  private String tag;
  private String rvContinue;

  public MWApiRevisions(MWApiQuery parent) {
    super(parent);
    rvPropsEnums = new ArrayList<>();
  }

  @Override
  public void buildUrl(URIBuilder builder) {
    super.buildUrl(builder);
    URIBuilderHelper.addParameter(builder, "rvprop", rvPropsEnums);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvlimit", rvLimit);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvexpandtemplates", expandTemplates);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvgeneratexml", generateXml);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvparse", parseRevision);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvsection", section);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvdiffto", diffTo);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvdifftotext", diffToText);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvcontentformat", contentFormat);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvstartid", startId);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvendid", endId);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvstart", rvStart);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvend", rvEnd);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvdir", sortDirection);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvuser", user);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvexcludeuser", excludeUser);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvtag", tag);
    URIBuilderHelper.addParameterIfNotEmpty(builder, "rvcontinue", rvContinue);
  }

  public RevisionsOption rvprop(RVPropEnum... rvPropsEnums) {
    Collections.addAll(this.rvPropsEnums, rvPropsEnums);
    return this;
  }

  @Override
  public RevisionsOption rvlimit(int limit) {
    this.rvLimit = limit;
    return this;
  }

  @Override
  public RevisionsOption rvexpandtemplates() {
    this.expandTemplates = true;
    return this;
  }

  @Override
  public RevisionsOption rvgeneratexml() {
    this.generateXml = true;
    return this;
  }

  @Override
  public RevisionsOption rvparse() {
    this.parseRevision = true;
    return this;
  }

  @Override
  public RevisionsOption rvsection(int section) {
    this.section = section;
    return this;
  }

  @Override
  public RevisionsOption rvdiffto(RVDiffEnum diff) {
    this.diffTo = diff;
    return this;
  }

  @Override
  public RevisionsOption rvdifftotext() {
    this.diffToText = true;
    return this;
  }

  @Override
  public RevisionsOption rvcontentformat(RVContentFormatEnum contentFormat) {
    this.contentFormat = contentFormat;
    return this;
  }

  @Override
  public RevisionsOption rvstartid(int id) {
    this.startId = id;
    return this;
  }

  @Override
  public RevisionsOption rvendid(int id) {
    this.endId = id;
    return this;
  }

  @Override
  public RevisionsOption rvstart(DateTime dt) {
    this.rvStart = dt;
    return this;
  }

  @Override
  public RevisionsOption rvend(DateTime dt) {
    this.rvEnd = dt;
    return this;
  }

  @Override
  public RevisionsOption rvdir(SortDirectionByTimeEnum direction) {
    this.sortDirection = direction;
    return this;
  }

  @Override
  public RevisionsOption rvuser(String userName) {
    this.user = userName;
    return this;
  }

  @Override
  public RevisionsOption rvexcludeuser(String userName) {
    this.excludeUser = userName;
    return this;
  }

  @Override
  public RevisionsOption rvtag(String tag) {
    this.tag = tag;
    return this;
  }

  @Override
  public RevisionsOption rvcontinue(String cont) {
    this.rvContinue = cont;
    return this;
  }


}
