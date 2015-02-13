package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.decorator.MWApiBase;
import com.wikia.mwapi.enumtypes.ListEnum;
import com.wikia.mwapi.enumtypes.PropEnum;
import com.wikia.mwapi.util.URIBuilderHelper;

import org.apache.http.client.utils.URIBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MWApiQuery extends MWApiQueryBase {

  private List<String> titles;
  private List<ListEnum> lists;
  private List<PropEnum> props;
  private List<Long> revIds;

  public MWApiQuery(MWApiBase parent) {
    super(parent);
    lists = new ArrayList<>();
    props = new ArrayList<>();
    titles = new ArrayList<>();
    revIds = new ArrayList<>();
  }

  @Override
  protected void buildUrl(URIBuilder builder) {
    super.buildUrl(builder);
    URIBuilderHelper.addParameter(builder, "titles", titles);
    URIBuilderHelper.addParameter(builder, "list", lists);
    URIBuilderHelper.addParameter(builder, "prop", props);
    URIBuilderHelper.addParameter(builder, "revids", revIds);
  }

  @Override
  public void titlesLogic(String... titles) {
    Collections.addAll(this.titles, titles);
  }

  @Override
  public void revIdsLogic(Long... revIds) {
    Collections.addAll(this.revIds, revIds);
  }

  @Override
  public void allPagesLogic() {
    lists.add(ListEnum.ALLPAGES);
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
    this.props.add(PropEnum.CATEGORIES);
  }

  @Override
  public void categoryinfoLogic() {
    this.props.add(PropEnum.CATEGORYINFO);
  }

  @Override
  public void contributorsLogic() {
    this.props.add(PropEnum.CONTRIBUTORS);
  }

  @Override
  public void coordinatesLogic() {
    this.props.add(PropEnum.COORDINATES);
  }

  @Override
  public void deletedrevisionsLogic() {
    this.props.add(PropEnum.DELETEDREVISIONS);
  }

  @Override
  public void duplicatefilesLogic() {
    this.props.add(PropEnum.DUPLICATEFILES);
  }

  @Override
  public void extlinksLogic() {
    this.props.add(PropEnum.EXTLINKS);
  }

  @Override
  public void extractsLogic() {
    this.props.add(PropEnum.EXTRACTS);
  }

  @Override
  public void fileusageLogic() {
    this.props.add(PropEnum.FILEUSAGE);
  }

  @Override
  public void flaggedLogic() {
    this.props.add(PropEnum.FLAGGED);
  }

  @Override
  public void flowinfoLogic() {
    this.props.add(PropEnum.FLOWINFO);
  }

  @Override
  public void globalusageLogic() {
    this.props.add(PropEnum.GLOBALUSAGE);
  }

  @Override
  public void imageinfoLogic() {
    this.props.add(PropEnum.IMAGEINFO);
  }

  @Override
  public void imagesLogic() {
    this.props.add(PropEnum.IMAGES);
  }

  @Override
  public void infoLogic() {
    this.props.add(PropEnum.INFO);
  }

  @Override
  public void iwlinksLogic() {
    this.props.add(PropEnum.IWLINKS);
  }

  @Override
  public void langlinksLogic() {
    this.props.add(PropEnum.LANGLINKS);
  }

  @Override
  public void linksLogic() {
    this.props.add(PropEnum.LINKS);
  }

  @Override
  public void linkshereLogic() {
    this.props.add(PropEnum.LINKSHERE);
  }

  @Override
  public void pageimagesLogic() {
    this.props.add(PropEnum.PAGEIMAGES);
  }

  @Override
  public void pagepropsLogic() {
    this.props.add(PropEnum.PAGEPROPS);
  }

  @Override
  public void pagetermsLogic() {
    this.props.add(PropEnum.PAGETERMS);
  }

  @Override
  public void redirectsLogic() {
    this.props.add(PropEnum.REDIRECTS);
  }

  @Override
  public void revisionsLogic() {
    this.props.add(PropEnum.REVISIONS);
  }

  @Override
  public void stashimageinfoLogic() {
    this.props.add(PropEnum.STASHIMAGEINFO);
  }

  @Override
  public void templatesLogic() {
    this.props.add(PropEnum.TEMPLATES);
  }

  @Override
  public void transcludedinLogic() {
    this.props.add(PropEnum.TRANSCLUDEDIN);
  }

  @Override
  public void transcodestatusLogic() {
    this.props.add(PropEnum.TRANSCODESTATUS);
  }

  @Override
  public void videoinfoLogic() {
    this.props.add(PropEnum.VIDEOINFO);
  }

  @Override
  protected void abusefiltersLogic() {

  }

  @Override
  protected void abuselogLogic() {
    this.lists.add(ListEnum.ABUSELOG);
  }

  @Override
  protected void allcategoriesLogic() {
    this.lists.add(ListEnum.ALLCATEGORIES);
  }

  @Override
  protected void alldeletedrevisionsLogic() {
    this.lists.add(ListEnum.ALLDELETEDREVISIONS);
  }

  @Override
  protected void allfileusagesLogic() {
    this.lists.add(ListEnum.ALLFILEUSAGES);
  }

  @Override
  protected void allimagesLogic() {
    this.lists.add(ListEnum.ALLIMAGES);
  }

  @Override
  protected void alllinksLogic() {
    this.lists.add(ListEnum.ALLLINKS);
  }

  @Override
  protected void allpagesLogic() {
    this.lists.add(ListEnum.ALLPAGES);
  }

  @Override
  protected void allredirectsLogic() {
    this.lists.add(ListEnum.ALLREDIRECTS);
  }

  @Override
  protected void alltransclusionsLogic() {
    this.lists.add(ListEnum.ALLTRANSCLUSIONS);
  }

  @Override
  protected void allusersLogic() {
    this.lists.add(ListEnum.ALLUSERS);
  }

  @Override
  protected void backlinksLogic() {
    this.lists.add(ListEnum.BACKLINKS);
  }

  @Override
  protected void betafeaturesLogic() {
    this.lists.add(ListEnum.BETAFEATURES);
  }

  @Override
  protected void blocksLogic() {
    this.lists.add(ListEnum.BLOCKS);
  }

  @Override
  protected void categorymembersLogic() {
    this.lists.add(ListEnum.CATEGORYMEMBERS);
  }

  @Override
  protected void centralnoticelogsLogic() {
    this.lists.add(ListEnum.CENTRALNOTICELOGS);
  }

  @Override
  protected void checkuserLogic() {
    this.lists.add(ListEnum.CHECKUSER);
  }

  @Override
  protected void checkuserlogLogic() {
    this.lists.add(ListEnum.CHECKUSERLOG);
  }

  @Override
  protected void deletedrevsLogic() {
    this.lists.add(ListEnum.DELETEDREVS);
  }

  @Override
  protected void embeddedinLogic() {
    this.lists.add(ListEnum.EMBEDDEDIN);
  }

  @Override
  protected void exturlusageLogic() {
    this.lists.add(ListEnum.EXTURLUSAGE);
  }

  @Override
  protected void filearchiveLogic() {
    this.lists.add(ListEnum.FILEARCHIVE);
  }

  @Override
  protected void gadgetcategoriesLogic() {
    this.lists.add(ListEnum.GADGETCATEGORIES);
  }

  @Override
  protected void gadgetsLogic() {
    this.lists.add(ListEnum.GADGETS);
  }

  @Override
  protected void geosearchLogic() {
    this.lists.add(ListEnum.GEOSEARCH);
  }

  @Override
  protected void gettingstartedgetpagesLogic() {
    this.lists.add(ListEnum.GETTINGSTARTEDGETPAGES);
  }

  @Override
  protected void globalallusersLogic() {
    this.lists.add(ListEnum.GLOBALALLUSERS);
  }

  @Override
  protected void globalblocksLogic() {
    this.lists.add(ListEnum.GLOBALBLOCKS);
  }

  @Override
  protected void globalgroupsLogic() {
    this.lists.add(ListEnum.GLOBALGROUPS);
  }

  @Override
  protected void imageusageLogic() {
    this.lists.add(ListEnum.IMAGEUSAGE);
  }

  @Override
  protected void iwbacklinksLogic() {
    this.lists.add(ListEnum.IWBACKLINKS);
  }

  @Override
  protected void langbacklinksLogic() {
    this.lists.add(ListEnum.LANGBACKLINKS);
  }

  @Override
  protected void logeventsLogic() {
    this.lists.add(ListEnum.LOGEVENTS);
  }

  @Override
  protected void mmsitesLogic() {
    this.lists.add(ListEnum.MMSITES);
  }

  @Override
  protected void oldreviewedpagesLogic() {
    this.lists.add(ListEnum.OLDREVIEWEDPAGES);
  }

  @Override
  protected void pagepropnamesLogic() {
    this.lists.add(ListEnum.PAGEPROPNAMES);
  }

  @Override
  protected void pageswithpropLogic() {
    this.lists.add(ListEnum.PAGESWITHPROP);
  }

  @Override
  protected void prefixsearchLogic() {
    this.lists.add(ListEnum.PREFIXSEARCH);
  }

  @Override
  protected void protectedtitlesLogic() {
    this.lists.add(ListEnum.PROTECTEDTITLES);
  }

  @Override
  protected void querypageLogic() {
    this.lists.add(ListEnum.QUERYPAGE);
  }

  @Override
  protected void randomLogic() {
    this.lists.add(ListEnum.RANDOM);
  }

  @Override
  protected void recentchangesLogic() {
    this.lists.add(ListEnum.RECENTCHANGES);
  }

  @Override
  protected void searchLogic() {
    this.lists.add(ListEnum.SEARCH);
  }

  @Override
  protected void tagsLogic() {
    this.lists.add(ListEnum.TAGS);
  }

  @Override
  protected void usercontribsLogic() {
    this.lists.add(ListEnum.USERCONTRIBS);
  }

  @Override
  protected void usersLogic() {
    this.lists.add(ListEnum.USERS);
  }

  @Override
  protected void watchlistLogic() {
    this.lists.add(ListEnum.WATCHLIST);
  }

  @Override
  protected void watchlistrawLogic() {
    this.lists.add(ListEnum.WATCHLISTRAW);
  }

  @Override
  protected void wikigrokrandomLogic() {
    this.lists.add(ListEnum.WIKIGROKRANDOM);
  }

  @Override
  protected void wikisetsLogic() {
    this.lists.add(ListEnum.WIKISETS);
  }
}
