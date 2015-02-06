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

  @Override
  protected void abusefiltersLogic() {

  }

  @Override
  protected void abuselogLogic() {
    this.lists.add(ListEnum.abuselog);
  }

  @Override
  protected void allcategoriesLogic() {
    this.lists.add(ListEnum.allcategories);
  }

  @Override
  protected void alldeletedrevisionsLogic() {
    this.lists.add(ListEnum.alldeletedrevisions);
  }

  @Override
  protected void allfileusagesLogic() {
    this.lists.add(ListEnum.allfileusages);
  }

  @Override
  protected void allimagesLogic() {
    this.lists.add(ListEnum.allimages);
  }

  @Override
  protected void alllinksLogic() {
    this.lists.add(ListEnum.alllinks);
  }

  @Override
  protected void allpagesLogic() {
    this.lists.add(ListEnum.allpages);
  }

  @Override
  protected void allredirectsLogic() {
    this.lists.add(ListEnum.allredirects);
  }

  @Override
  protected void alltransclusionsLogic() {
    this.lists.add(ListEnum.alltransclusions);
  }

  @Override
  protected void allusersLogic() {
    this.lists.add(ListEnum.allusers);
  }

  @Override
  protected void backlinksLogic() {
    this.lists.add(ListEnum.backlinks);
  }

  @Override
  protected void betafeaturesLogic() {
    this.lists.add(ListEnum.betafeatures);
  }

  @Override
  protected void blocksLogic() {
    this.lists.add(ListEnum.blocks);
  }

  @Override
  protected void categorymembersLogic() {
    this.lists.add(ListEnum.categorymembers);
  }

  @Override
  protected void centralnoticelogsLogic() {
    this.lists.add(ListEnum.centralnoticelogs);
  }

  @Override
  protected void checkuserLogic() {
    this.lists.add(ListEnum.checkuser);
  }

  @Override
  protected void checkuserlogLogic() {
    this.lists.add(ListEnum.checkuserlog);
  }

  @Override
  protected void deletedrevsLogic() {
    this.lists.add(ListEnum.deletedrevs);
  }

  @Override
  protected void embeddedinLogic() {
    this.lists.add(ListEnum.embeddedin);
  }

  @Override
  protected void exturlusageLogic() {
    this.lists.add(ListEnum.exturlusage);
  }

  @Override
  protected void filearchiveLogic() {
    this.lists.add(ListEnum.filearchive);
  }

  @Override
  protected void gadgetcategoriesLogic() {
    this.lists.add(ListEnum.gadgetcategories);
  }

  @Override
  protected void gadgetsLogic() {
    this.lists.add(ListEnum.gadgets);
  }

  @Override
  protected void geosearchLogic() {
    this.lists.add(ListEnum.geosearch);
  }

  @Override
  protected void gettingstartedgetpagesLogic() {
    this.lists.add(ListEnum.gettingstartedgetpages);
  }

  @Override
  protected void globalallusersLogic() {
    this.lists.add(ListEnum.globalallusers);
  }

  @Override
  protected void globalblocksLogic() {
    this.lists.add(ListEnum.globalblocks);
  }

  @Override
  protected void globalgroupsLogic() {
    this.lists.add(ListEnum.globalgroups);
  }

  @Override
  protected void imageusageLogic() {
    this.lists.add(ListEnum.imageusage);
  }

  @Override
  protected void iwbacklinksLogic() {
    this.lists.add(ListEnum.iwbacklinks);
  }

  @Override
  protected void langbacklinksLogic() {
    this.lists.add(ListEnum.langbacklinks);
  }

  @Override
  protected void logeventsLogic() {
    this.lists.add(ListEnum.logevents);
  }

  @Override
  protected void mmsitesLogic() {
    this.lists.add(ListEnum.mmsites);
  }

  @Override
  protected void oldreviewedpagesLogic() {
    this.lists.add(ListEnum.oldreviewedpages);
  }

  @Override
  protected void pagepropnamesLogic() {
    this.lists.add(ListEnum.pagepropnames);
  }

  @Override
  protected void pageswithpropLogic() {
    this.lists.add(ListEnum.pageswithprop);
  }

  @Override
  protected void prefixsearchLogic() {
    this.lists.add(ListEnum.prefixsearch);
  }

  @Override
  protected void protectedtitlesLogic() {
    this.lists.add(ListEnum.protectedtitles);
  }

  @Override
  protected void querypageLogic() {
    this.lists.add(ListEnum.querypage);
  }

  @Override
  protected void randomLogic() {
    this.lists.add(ListEnum.random);
  }

  @Override
  protected void recentchangesLogic() {
    this.lists.add(ListEnum.recentchanges);
  }

  @Override
  protected void searchLogic() {
    this.lists.add(ListEnum.search);
  }

  @Override
  protected void tagsLogic() {
    this.lists.add(ListEnum.tags);
  }

  @Override
  protected void usercontribsLogic() {
    this.lists.add(ListEnum.usercontribs);
  }

  @Override
  protected void usersLogic() {
    this.lists.add(ListEnum.users);
  }

  @Override
  protected void watchlistLogic() {
    this.lists.add(ListEnum.watchlist);
  }

  @Override
  protected void watchlistrawLogic() {
    this.lists.add(ListEnum.watchlistraw);
  }

  @Override
  protected void wikigrokrandomLogic() {
    this.lists.add(ListEnum.wikigrokrandom);
  }

  @Override
  protected void wikisetsLogic() {
    this.lists.add(ListEnum.wikisets);
  }
}
