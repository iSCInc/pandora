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

import java.util.Collections;

public abstract class MWApiQueryBase extends MWApiDecorator implements TitlesChoose, QueryOption {

  public MWApiQueryBase(MWApiBase parent) {
    super(parent);
  }

  @Override
  public QueryOption titles(String... titles) {
    titlesLogic(titles);
    return this;
  }

  protected abstract void titlesLogic(String[] titles);

  @Override
  public QueryOption allPages() {
    allPagesLogic();
    return this;
  }

  protected abstract void allPagesLogic();

  @Override
  public QueryOption list(ListEnum... enums) {
    listLogic(enums);
    return this;
  }

  protected abstract void listLogic(ListEnum... enums);

  @Override
  public QueryOption prop(PropEnum... propEnums) {
    propLogic(propEnums);
    return this;
  }

  protected abstract void propLogic(PropEnum... propEnums);

  public CategoriesOption categories() {
    categoriesLogic();
    return new MWApiCategories(this);
  }

  protected abstract void categoriesLogic();

  public CategoryInfoOption categoryinfo() {
    categoryinfoLogic();
    return new MWApiCategoryInfo(this);
  }

  protected abstract void categoryinfoLogic();


  public ContributorsOption contributors() {
    contributorsLogic();
    return new MWApiContributors(this);
  }

  protected abstract void contributorsLogic();


  public CoordinatesOption coordinates() {
    coordinatesLogic();
    return new MWApiCoordinates(this);
  }

  protected abstract void coordinatesLogic();


  public DeletedRevisionsOption deletedrevisions() {
    deletedrevisionsLogic();
    return new MWApiDeletedRevisions(this);
  }

  protected abstract void deletedrevisionsLogic();


  public DuplicateFilesOption duplicatefiles() {
    duplicatefilesLogic();
    return new MWApiDuplicateFiles(this);
  }

  protected abstract void duplicatefilesLogic();


  public ExtLinksOption extlinks() {
    extlinksLogic();
    return new MWApiExtLinks(this);
  }

  protected abstract void extlinksLogic();


  public ExtractsOption extracts() {
    extractsLogic();
    return new MWApiExtracts(this);
  }

  protected abstract void extractsLogic();


  public FileUsageOption fileusage() {
    fileusageLogic();
    return new MWApiFileUsage(this);
  }

  protected abstract void fileusageLogic();


  public FlaggedOption flagged() {
    flaggedLogic();
    return new MWApiFlagged(this);
  }

  protected abstract void flaggedLogic();


  public FlowInfoOption flowinfo() {
    flowinfoLogic();
    return new MWApiFlowInfo(this);
  }

  protected abstract void flowinfoLogic();


  public GlobalUsageOption globalusage() {
    globalusageLogic();
    return new MWApiGlobalUsage(this);
  }

  protected abstract void globalusageLogic();


  public ImageInfoOption imageinfo() {
    imageinfoLogic();
    return new MWApiImageInfo(this);
  }

  protected abstract void imageinfoLogic();


  public ImagesOption images() {
    imagesLogic();
    return new MWApiImages(this);
  }

  protected abstract void imagesLogic();


  public InfoOption info() {
    infoLogic();
    return new MWApiInfo(this);
  }

  protected abstract void infoLogic();


  public IWLinksOption iwlinks() {
    iwlinksLogic();
    return new MWApiIWLinks(this);
  }

  protected abstract void iwlinksLogic();


  public LangLinksOption langlinks() {
    langlinksLogic();
    return new MWApiLangLinks(this);
  }

  protected abstract void langlinksLogic();


  public LinksOption links() {
    linksLogic();
    return new MWApiLinks(this);
  }

  protected abstract void linksLogic();


  public LinksHereOption linkshere() {
    linkshereLogic();
    return new MWApiLinksHere(this);
  }

  protected abstract void linkshereLogic();


  public PageImagesOption pageimages() {
    pageimagesLogic();
    return new MWApiPageImages(this);
  }

  protected abstract void pageimagesLogic();


  public PagePropsOption pageprops() {
    pagepropsLogic();
    return new MWApiPageProps(this);
  }

  protected abstract void pagepropsLogic();


  public PageTermsOption pageterms() {
    pagetermsLogic();
    return new MWApiPageTerms(this);
  }

  protected abstract void pagetermsLogic();


  public RedirectsOption redirects() {
    redirectsLogic();
    return new MWApiRedirects(this);
  }

  protected abstract void redirectsLogic();


  public RevisionsOption revisions() {
    revisionsLogic();
    return new MWApiRevisions(this);
  }

  protected abstract void revisionsLogic();


  public StashImageInfoOption stashimageinfo() {
    stashimageinfoLogic();
    return new MWApiStashImageInfo(this);
  }

  protected abstract void stashimageinfoLogic();


  public TemplatesOption templates() {
    templatesLogic();
    return new MWApiTemplates(this);
  }

  protected abstract void templatesLogic();


  public TranscludedInOption transcludedin() {
    transcludedinLogic();
    return new MWApiTranscludedIn(this);
  }

  protected abstract void transcludedinLogic();


  public TranscodeStatusOption transcodestatus() {
    transcodestatusLogic();
    return new MWApiTranscodeStatus(this);
  }

  protected abstract void transcodestatusLogic();


  public VideoInfoOption videoinfo() {
    videoinfoLogic();
    return new MWApiVideoInfo(this);
  }

  protected abstract void videoinfoLogic();
}
