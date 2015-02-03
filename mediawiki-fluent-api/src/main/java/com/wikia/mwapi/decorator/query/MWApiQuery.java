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

public class MWApiQuery extends MWApiDecorator implements TitlesChoose, QueryOption {

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
  public QueryOption titles(String... titles) {
    Collections.addAll(this.titles, titles);
    return this;
  }

  @Override
  public QueryOption allPages() {
    lists.add(ListEnum.allpages);
    return this;
  }

  @Override
  public QueryOption list(ListEnum... enums) {
    Collections.addAll(lists, enums);
    return this;
  }

  @Override
  public QueryOption prop(PropEnum... propEnums) {
    Collections.addAll(props, propEnums);
    return this;
  }

  @Override
  public CategoriesOption categories() {
    this.props.add(PropEnum.categories);
    return new MWApiCategories(this);
  }

  @Override
  public CategoryInfoOption categoryinfo() {
    this.props.add(PropEnum.categoryinfo);
    return new MWApiCategoryInfo(this);
  }

  @Override
  public ContributorsOption contributors() {
    this.props.add(PropEnum.contributors);
    return new MWApiContributors(this);
  }

  @Override
  public CoordinatesOption coordinates() {
    this.props.add(PropEnum.coordinates);
    return new MWApiCoordinates(this);
  }

  @Override
  public DeletedRevisionsOption deletedrevisions() {
    this.props.add(PropEnum.deletedrevisions);
    return new MWApiDeletedRevisions(this);
  }

  @Override
  public DuplicateFilesOption duplicatefiles() {
    this.props.add(PropEnum.duplicatefiles);
    return new MWApiDuplicateFiles(this);
  }

  @Override
  public ExtLinksOption extlinks() {
    this.props.add(PropEnum.extlinks);
    return new MWApiExtLinks(this);
  }

  @Override
  public ExtractsOption extracts() {
    this.props.add(PropEnum.extracts);
    return new MWApiExtracts(this);
  }

  @Override
  public FileUsageOption fileusage() {
    this.props.add(PropEnum.fileusage);
    return new MWApiFileUsage(this);
  }

  @Override
  public FlaggedOption flagged() {
    this.props.add(PropEnum.flagged);
    return new MWApiFlagged(this);
  }

  @Override
  public FlowInfoOption flowinfo() {
    this.props.add(PropEnum.flowinfo);
    return new MWApiFlowInfo(this);
  }

  @Override
  public GlobalUsageOption globalusage() {
    this.props.add(PropEnum.globalusage);
    return new MWApiGlobalUsage(this);
  }

  @Override
  public ImageInfoOption imageinfo() {
    this.props.add(PropEnum.imageinfo);
    return new MWApiImageInfo(this);
  }

  @Override
  public ImagesOption images() {
    this.props.add(PropEnum.images);
    return new MWApiImages(this);
  }

  @Override
  public InfoOption info() {
    this.props.add(PropEnum.info);
    return new MWApiInfo(this);
  }

  @Override
  public IWLinksOption iwlinks() {
    this.props.add(PropEnum.iwlinks);
    return new MWApiIWLinks(this);
  }

  @Override
  public LangLinksOption langlinks() {
    this.props.add(PropEnum.langlinks);
    return new MWApiLangLinks(this);
  }

  @Override
  public LinksOption links() {
    this.props.add(PropEnum.links);
    return new MWApiLinks(this);
  }

  @Override
  public LinksHereOption linkshere() {
    this.props.add(PropEnum.linkshere);
    return new MWApiLinksHere(this);
  }

  @Override
  public PageImagesOption pageimages() {
    this.props.add(PropEnum.pageimages);
    return new MWApiPageImages(this);
  }

  @Override
  public PagePropsOption pageprops() {
    this.props.add(PropEnum.pageprops);
    return new MWApiPageProps(this);
  }

  @Override
  public PageTermsOption pageterms() {
    this.props.add(PropEnum.pageterms);
    return new MWApiPageTerms(this);
  }

  @Override
  public RedirectsOption redirects() {
    this.props.add(PropEnum.redirects);
    return new MWApiRedirects(this);
  }

  @Override
  public RevisionsOption revisions() {
    this.props.add(PropEnum.revisions);
    return new MWApiRevisions(this);
  }

  @Override
  public StashImageInfoOption stashimageinfo() {
    this.props.add(PropEnum.stashimageinfo);
    return new MWApiStashImageInfo(this);
  }

  @Override
  public TemplatesOption templates() {
    this.props.add(PropEnum.templates);
    return new MWApiTemplates(this);
  }

  @Override
  public TranscludedInOption transcludedin() {
    this.props.add(PropEnum.transcludedin);
    return new MWApiTranscludedIn(this);
  }

  @Override
  public TranscodeStatusOption transcodestatus() {
    this.props.add(PropEnum.transcodestatus);
    return new MWApiTranscodeStatus(this);
  }

  @Override
  public VideoInfoOption videoinfo() {
    this.props.add(PropEnum.videoinfo);
    return new MWApiVideoInfo(this);
  }
}
