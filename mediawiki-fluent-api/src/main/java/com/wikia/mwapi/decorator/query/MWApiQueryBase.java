package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.decorator.MWApiBase;
import com.wikia.mwapi.decorator.MWApiDecorator;
import com.wikia.mwapi.decorator.query.list.MWApiAllCategories;
import com.wikia.mwapi.decorator.query.list.MWApiCategoryMembers;
import com.wikia.mwapi.decorator.query.property.MWApiCategories;
import com.wikia.mwapi.decorator.query.property.MWApiCategoryInfo;
import com.wikia.mwapi.decorator.query.property.MWApiContributors;
import com.wikia.mwapi.decorator.query.property.MWApiCoordinates;
import com.wikia.mwapi.decorator.query.property.MWApiDeletedRevisions;
import com.wikia.mwapi.decorator.query.property.MWApiDuplicateFiles;
import com.wikia.mwapi.decorator.query.property.MWApiExtLinks;
import com.wikia.mwapi.decorator.query.property.MWApiExtracts;
import com.wikia.mwapi.decorator.query.property.MWApiFileUsage;
import com.wikia.mwapi.decorator.query.property.MWApiFlagged;
import com.wikia.mwapi.decorator.query.property.MWApiFlowInfo;
import com.wikia.mwapi.decorator.query.property.MWApiGlobalUsage;
import com.wikia.mwapi.decorator.query.property.MWApiIWLinks;
import com.wikia.mwapi.decorator.query.property.MWApiImageInfo;
import com.wikia.mwapi.decorator.query.property.MWApiImages;
import com.wikia.mwapi.decorator.query.property.MWApiInfo;
import com.wikia.mwapi.decorator.query.property.MWApiLangLinks;
import com.wikia.mwapi.decorator.query.property.MWApiLinks;
import com.wikia.mwapi.decorator.query.property.MWApiLinksHere;
import com.wikia.mwapi.decorator.query.property.MWApiPageImages;
import com.wikia.mwapi.decorator.query.property.MWApiPageProps;
import com.wikia.mwapi.decorator.query.property.MWApiPageTerms;
import com.wikia.mwapi.decorator.query.property.MWApiRedirects;
import com.wikia.mwapi.decorator.query.property.MWApiRevisions;
import com.wikia.mwapi.decorator.query.property.MWApiStashImageInfo;
import com.wikia.mwapi.decorator.query.property.MWApiTemplates;
import com.wikia.mwapi.decorator.query.property.MWApiTranscludedIn;
import com.wikia.mwapi.decorator.query.property.MWApiTranscodeStatus;
import com.wikia.mwapi.decorator.query.property.MWApiVideoInfo;
import com.wikia.mwapi.enumtypes.ListEnum;
import com.wikia.mwapi.enumtypes.PropEnum;
import com.wikia.mwapi.fluent.QueryOption;
import com.wikia.mwapi.fluent.TitlesOrListChoose;
import com.wikia.mwapi.fluent.query.AllCategoriesOption;
import com.wikia.mwapi.fluent.query.CategoriesOption;
import com.wikia.mwapi.fluent.query.CategoryInfoOption;
import com.wikia.mwapi.fluent.query.CategoryMembersOption;
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

public abstract class MWApiQueryBase extends MWApiDecorator
    implements TitlesOrListChoose, QueryOption {

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

  @Override
  public QueryOption revIds(Long... revIds) {
    revIdsLogic(revIds);
    return this;
  }

  protected abstract void revIdsLogic(Long... revIds);

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


  public QueryOption abusefilters() {
    abusefiltersLogic();
    return this;
  }

  protected abstract void abusefiltersLogic();

  public QueryOption abuselog() {
    abuselogLogic();
    return this;
  }

  protected abstract void abuselogLogic();

  public AllCategoriesOption allcategories() {
    allcategoriesLogic();
    return new MWApiAllCategories(this);
  }

  protected abstract void allcategoriesLogic();

  public QueryOption alldeletedrevisions() {
    alldeletedrevisionsLogic();
    return this;
  }

  protected abstract void alldeletedrevisionsLogic();

  public QueryOption allfileusages() {
    allfileusagesLogic();
    return this;
  }

  protected abstract void allfileusagesLogic();

  public QueryOption allimages() {
    allimagesLogic();
    return this;
  }

  protected abstract void allimagesLogic();

  public QueryOption alllinks() {
    alllinksLogic();
    return this;
  }

  protected abstract void alllinksLogic();

  public QueryOption allpages() {
    allpagesLogic();
    return this;
  }

  protected abstract void allpagesLogic();

  public QueryOption allredirects() {
    allredirectsLogic();
    return this;
  }

  protected abstract void allredirectsLogic();

  public QueryOption alltransclusions() {
    alltransclusionsLogic();
    return this;
  }

  protected abstract void alltransclusionsLogic();

  public QueryOption allusers() {
    allusersLogic();
    return this;
  }

  protected abstract void allusersLogic();

  public QueryOption backlinks() {
    backlinksLogic();
    return this;
  }

  protected abstract void backlinksLogic();

  public QueryOption betafeatures() {
    betafeaturesLogic();
    return this;
  }

  protected abstract void betafeaturesLogic();

  public QueryOption blocks() {
    blocksLogic();
    return this;
  }

  protected abstract void blocksLogic();

  public CategoryMembersOption categorymembers() {
    categorymembersLogic();
    return new MWApiCategoryMembers(this);
  }

  protected abstract void categorymembersLogic();


  public QueryOption centralnoticelogs() {
    centralnoticelogsLogic();
    return this;
  }

  protected abstract void centralnoticelogsLogic();

  public QueryOption checkuser() {
    checkuserLogic();
    return this;
  }

  protected abstract void checkuserLogic();

  public QueryOption checkuserlog() {
    checkuserlogLogic();
    return this;
  }

  protected abstract void checkuserlogLogic();

  public QueryOption deletedrevs() {
    deletedrevsLogic();
    return this;
  }

  protected abstract void deletedrevsLogic();

  public QueryOption embeddedin() {
    embeddedinLogic();
    return this;
  }

  protected abstract void embeddedinLogic();

  public QueryOption exturlusage() {
    exturlusageLogic();
    return this;
  }

  protected abstract void exturlusageLogic();

  public QueryOption filearchive() {
    filearchiveLogic();
    return this;
  }

  protected abstract void filearchiveLogic();

  public QueryOption gadgetcategories() {
    gadgetcategoriesLogic();
    return this;
  }

  protected abstract void gadgetcategoriesLogic();

  public QueryOption gadgets() {
    gadgetsLogic();
    return this;
  }

  protected abstract void gadgetsLogic();

  public QueryOption geosearch() {
    geosearchLogic();
    return this;
  }

  protected abstract void geosearchLogic();

  public QueryOption gettingstartedgetpages() {
    gettingstartedgetpagesLogic();
    return this;
  }

  protected abstract void gettingstartedgetpagesLogic();

  public QueryOption globalallusers() {
    globalallusersLogic();
    return this;
  }

  protected abstract void globalallusersLogic();

  public QueryOption globalblocks() {
    globalblocksLogic();
    return this;
  }

  protected abstract void globalblocksLogic();

  public QueryOption globalgroups() {
    globalgroupsLogic();
    return this;
  }

  protected abstract void globalgroupsLogic();

  public QueryOption imageusage() {
    imageusageLogic();
    return this;
  }

  protected abstract void imageusageLogic();

  public QueryOption iwbacklinks() {
    iwbacklinksLogic();
    return this;
  }

  protected abstract void iwbacklinksLogic();

  public QueryOption langbacklinks() {
    langbacklinksLogic();
    return this;
  }

  protected abstract void langbacklinksLogic();

  public QueryOption logevents() {
    logeventsLogic();
    return this;
  }

  protected abstract void logeventsLogic();

  public QueryOption mmsites() {
    mmsitesLogic();
    return this;
  }

  protected abstract void mmsitesLogic();

  public QueryOption oldreviewedpages() {
    oldreviewedpagesLogic();
    return this;
  }

  protected abstract void oldreviewedpagesLogic();

  public QueryOption pagepropnames() {
    pagepropnamesLogic();
    return this;
  }

  protected abstract void pagepropnamesLogic();

  public QueryOption pageswithprop() {
    pageswithpropLogic();
    return this;
  }

  protected abstract void pageswithpropLogic();

  public QueryOption prefixsearch() {
    prefixsearchLogic();
    return this;
  }

  protected abstract void prefixsearchLogic();

  public QueryOption protectedtitles() {
    protectedtitlesLogic();
    return this;
  }

  protected abstract void protectedtitlesLogic();

  public QueryOption querypage() {
    querypageLogic();
    return this;
  }

  protected abstract void querypageLogic();

  public QueryOption random() {
    randomLogic();
    return this;
  }

  protected abstract void randomLogic();

  public QueryOption recentchanges() {
    recentchangesLogic();
    return this;
  }

  protected abstract void recentchangesLogic();

  public QueryOption search() {
    searchLogic();
    return this;
  }

  protected abstract void searchLogic();

  public QueryOption tags() {
    tagsLogic();
    return this;
  }

  protected abstract void tagsLogic();

  public QueryOption usercontribs() {
    usercontribsLogic();
    return this;
  }

  protected abstract void usercontribsLogic();

  public QueryOption users() {
    usersLogic();
    return this;
  }

  protected abstract void usersLogic();

  public QueryOption watchlist() {
    watchlistLogic();
    return this;
  }

  protected abstract void watchlistLogic();

  public QueryOption watchlistraw() {
    watchlistrawLogic();
    return this;
  }

  protected abstract void watchlistrawLogic();

  public QueryOption wikigrokrandom() {
    wikigrokrandomLogic();
    return this;
  }

  protected abstract void wikigrokrandomLogic();

  public QueryOption wikisets() {
    wikisetsLogic();
    return this;
  }

  protected abstract void wikisetsLogic();


}
