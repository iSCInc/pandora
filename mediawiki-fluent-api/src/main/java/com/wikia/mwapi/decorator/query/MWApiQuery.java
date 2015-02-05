package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.decorator.MWApiBase;
import com.wikia.mwapi.decorator.MWApiDecorator;
import com.wikia.mwapi.enumtypes.ListEnum;
import com.wikia.mwapi.enumtypes.PropEnum;
import com.wikia.mwapi.fluent.QueryOption;
import com.wikia.mwapi.fluent.TitlesChoose;
import com.wikia.mwapi.fluent.query.CategoriesOption;
import com.wikia.mwapi.fluent.query.CategoryInfoOption;
import com.wikia.mwapi.fluent.query.ContributorsOption;
import com.wikia.mwapi.fluent.query.CoordinatesOption;
import com.wikia.mwapi.fluent.query.DeletedRevisionsOption;
import com.wikia.mwapi.fluent.query.DuplicateFilesOption;
import com.wikia.mwapi.fluent.query.ExtLinksOption;
import com.wikia.mwapi.fluent.query.ExtractsOption;
import com.wikia.mwapi.fluent.query.FileUsageOption;
import com.wikia.mwapi.fluent.query.FlaggedOption;
import com.wikia.mwapi.fluent.query.FlowInfoOption;
import com.wikia.mwapi.fluent.query.GlobalUsageOption;
import com.wikia.mwapi.fluent.query.IWLinksOption;
import com.wikia.mwapi.fluent.query.ImageInfoOption;
import com.wikia.mwapi.fluent.query.ImagesOption;
import com.wikia.mwapi.fluent.query.InfoOption;
import com.wikia.mwapi.fluent.query.LangLinksOption;
import com.wikia.mwapi.fluent.query.LinksHereOption;
import com.wikia.mwapi.fluent.query.LinksOption;
import com.wikia.mwapi.fluent.query.PageImagesOption;
import com.wikia.mwapi.fluent.query.PagePropsOption;
import com.wikia.mwapi.fluent.query.PageTermsOption;
import com.wikia.mwapi.fluent.query.RedirectsOption;
import com.wikia.mwapi.fluent.query.RevisionsOption;
import com.wikia.mwapi.fluent.query.StashImageInfoOption;
import com.wikia.mwapi.fluent.query.TemplatesOption;
import com.wikia.mwapi.fluent.query.TranscludedInOption;
import com.wikia.mwapi.fluent.query.TranscodeStatusOption;
import com.wikia.mwapi.fluent.query.VideoInfoOption;
import com.wikia.mwapi.util.URIBuilderHelper;

import org.apache.http.client.utils.URIBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MWApiQuery extends MWApiQueryBase {

  private List<String> titles;
  private List<ListEnum> lists;
  private List<PropEnum> props;

  public MWApiQuery(MWApiBase parent) {
    super(parent);
    lists = new ArrayList<>();
    props = new ArrayList<>();
    titles = new ArrayList<>();
  }

  @Override
  protected void buildUrl(URIBuilder builder) {
    super.buildUrl(builder);
    URIBuilderHelper.addParameter(builder, "titles", titles);
    URIBuilderHelper.addParameter(builder, "list", lists);
    URIBuilderHelper.addParameter(builder, "prop", props);
  }

  @Override
  public void titlesLogic(String... titles) {
    Collections.addAll(this.titles, titles);
  }

  @Override
  public void allPagesLogic() {
    lists.add(ListEnum.allpages);
  }

  @Override
  public void listLogic(ListEnum... enums) {
    Collections.addAll(lists, enums);
  }

  @Override
  public void propLogic(PropEnum... propEnums) {
    Collections.addAll(props, propEnums);
  }

  @Override
  public void categoriesLogic() {
    this.props.add(PropEnum.categories);
  }

  @Override
  public void categoryinfoLogic() {
    this.props.add(PropEnum.categoryinfo);
  }

  @Override
  public void contributorsLogic() {
    this.props.add(PropEnum.contributors);
  }

  @Override
  public void coordinatesLogic() {
    this.props.add(PropEnum.coordinates);
  }

  @Override
  public void deletedrevisionsLogic() {
    this.props.add(PropEnum.deletedrevisions);
  }

  @Override
  public void duplicatefilesLogic() {
    this.props.add(PropEnum.duplicatefiles);
  }

  @Override
  public void extlinksLogic() {
    this.props.add(PropEnum.extlinks);
  }

  @Override
  public void extractsLogic() {
    this.props.add(PropEnum.extracts);
  }

  @Override
  public void fileusageLogic() {
    this.props.add(PropEnum.fileusage);
  }

  @Override
  public void flaggedLogic() {
    this.props.add(PropEnum.flagged);
  }

  @Override
  public void flowinfoLogic() {
    this.props.add(PropEnum.flowinfo);
  }

  @Override
  public void globalusageLogic() {
    this.props.add(PropEnum.globalusage);
  }

  @Override
  public void imageinfoLogic() {
    this.props.add(PropEnum.imageinfo);
  }

  @Override
  public void imagesLogic() {
    this.props.add(PropEnum.images);
  }

  @Override
  public void infoLogic() {
    this.props.add(PropEnum.info);
  }

  @Override
  public void iwlinksLogic() {
    this.props.add(PropEnum.iwlinks);
  }

  @Override
  public void langlinksLogic() {
    this.props.add(PropEnum.langlinks);
  }

  @Override
  public void linksLogic() {
    this.props.add(PropEnum.links);
  }

  @Override
  public void linkshereLogic() {
    this.props.add(PropEnum.linkshere);
  }

  @Override
  public void pageimagesLogic() {
    this.props.add(PropEnum.pageimages);
  }

  @Override
  public void pagepropsLogic() {
    this.props.add(PropEnum.pageprops);
  }

  @Override
  public void pagetermsLogic() {
    this.props.add(PropEnum.pageterms);
  }

  @Override
  public void redirectsLogic() {
    this.props.add(PropEnum.redirects);
  }

  @Override
  public void revisionsLogic() {
    this.props.add(PropEnum.revisions);
  }

  @Override
  public void stashimageinfoLogic() {
    this.props.add(PropEnum.stashimageinfo);
  }

  @Override
  public void templatesLogic() {
    this.props.add(PropEnum.templates);
  }

  @Override
  public void transcludedinLogic() {
    this.props.add(PropEnum.transcludedin);
  }

  @Override
  public void transcodestatusLogic() {
    this.props.add(PropEnum.transcodestatus);
  }

  @Override
  public void videoinfoLogic() {
    this.props.add(PropEnum.videoinfo);
  }
}
