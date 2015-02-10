package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.enumtypes.ListEnum;
import com.wikia.mwapi.enumtypes.PropEnum;

public class MWApiQueryDecorator extends MWApiQueryBase {


  private MWApiQueryBase parent;

  public MWApiQueryDecorator(MWApiQueryBase parent) {
    super(parent);
    this.parent = parent;
  }

  @Override
  protected void titlesLogic(String[] titles) {
    parent.titlesLogic(titles);
  }

  @Override
  protected void revIdsLogic(Long... revIds) {
    parent.revIdsLogic(revIds);
  }

  @Override
  protected void allPagesLogic() {
    parent.allPagesLogic();
  }

  @Override
  protected void listLogic(ListEnum... enums) {
    parent.listLogic(enums);
  }

  @Override
  protected void propLogic(PropEnum... propEnums) {
    parent.propLogic(propEnums);
  }

  @Override
  protected void categoriesLogic() {
    parent.categoriesLogic();
  }

  @Override
  protected void categoryinfoLogic() {
    parent.categoryinfoLogic();
  }

  @Override
  protected void contributorsLogic() {
    parent.contributorsLogic();
  }

  @Override
  protected void coordinatesLogic() {
    parent.coordinatesLogic();
  }

  @Override
  protected void deletedrevisionsLogic() {
    parent.deletedrevisionsLogic();
  }

  @Override
  protected void duplicatefilesLogic() {
    parent.duplicatefilesLogic();
  }

  @Override
  protected void extlinksLogic() {
    parent.extlinksLogic();
  }

  @Override
  protected void extractsLogic() {
    parent.extractsLogic();
  }

  @Override
  protected void fileusageLogic() {
    parent.fileusageLogic();
  }

  @Override
  protected void flaggedLogic() {
    parent.flaggedLogic();
  }

  @Override
  protected void flowinfoLogic() {
    parent.flowinfoLogic();
  }

  @Override
  protected void globalusageLogic() {
    parent.globalusageLogic();
  }

  @Override
  protected void imageinfoLogic() {
    parent.imageinfoLogic();
  }

  @Override
  protected void imagesLogic() {
    parent.imagesLogic();
  }

  @Override
  protected void infoLogic() {
    parent.infoLogic();
  }

  @Override
  protected void iwlinksLogic() {
    parent.iwlinksLogic();
  }

  @Override
  protected void langlinksLogic() {
    parent.langlinksLogic();
  }

  @Override
  protected void linksLogic() {
    parent.linksLogic();
  }

  @Override
  protected void linkshereLogic() {
    parent.linkshereLogic();
  }

  @Override
  protected void pageimagesLogic() {
    parent.pageimagesLogic();
  }

  @Override
  protected void pagepropsLogic() {
    parent.pagepropsLogic();
  }

  @Override
  protected void pagetermsLogic() {
    parent.pagetermsLogic();
  }

  @Override
  protected void redirectsLogic() {
    parent.redirectsLogic();
  }

  @Override
  protected void revisionsLogic() {
    parent.revisionsLogic();
  }

  @Override
  protected void stashimageinfoLogic() {
    parent.stashimageinfoLogic();
  }

  @Override
  protected void templatesLogic() {
    parent.templatesLogic();
  }

  @Override
  protected void transcludedinLogic() {
    parent.transcludedinLogic();
  }

  @Override
  protected void transcodestatusLogic() {
    parent.transcodestatusLogic();
  }

  @Override
  protected void videoinfoLogic() {
    parent.videoinfoLogic();
  }

  @Override
  protected void abusefiltersLogic() {

  }

  @Override
  protected void abuselogLogic() {
    parent.abuselogLogic();
  }

  @Override
  protected void allcategoriesLogic() {
    parent.allcategoriesLogic();
  }

  @Override
  protected void alldeletedrevisionsLogic() {
    parent.alldeletedrevisionsLogic();
  }

  @Override
  protected void allfileusagesLogic() {
    parent.allfileusagesLogic();
  }

  @Override
  protected void allimagesLogic() {
    parent.allimagesLogic();
  }

  @Override
  protected void alllinksLogic() {
    parent.alllinksLogic();
  }

  @Override
  protected void allpagesLogic() {
    parent.allpagesLogic();
  }

  @Override
  protected void allredirectsLogic() {
    parent.allredirectsLogic();
  }

  @Override
  protected void alltransclusionsLogic() {
    parent.alltransclusionsLogic();
  }

  @Override
  protected void allusersLogic() {
    parent.allusersLogic();
  }

  @Override
  protected void backlinksLogic() {
    parent.backlinksLogic();
  }

  @Override
  protected void betafeaturesLogic() {
    parent.betafeaturesLogic();
  }

  @Override
  protected void blocksLogic() {
    parent.blocksLogic();
  }

  @Override
  protected void categorymembersLogic() {
    parent.categorymembersLogic();
  }

  @Override
  protected void centralnoticelogsLogic() {
    parent.centralnoticelogsLogic();
  }

  @Override
  protected void checkuserLogic() {
    parent.checkuserLogic();
  }

  @Override
  protected void checkuserlogLogic() {
    parent.checkuserlogLogic();
  }

  @Override
  protected void deletedrevsLogic() {
    parent.deletedrevsLogic();
  }

  @Override
  protected void embeddedinLogic() {
    parent.embeddedinLogic();
  }

  @Override
  protected void exturlusageLogic() {
    parent.exturlusageLogic();
  }

  @Override
  protected void filearchiveLogic() {
    parent.filearchiveLogic();
  }

  @Override
  protected void gadgetcategoriesLogic() {
    parent.gadgetcategoriesLogic();
  }

  @Override
  protected void gadgetsLogic() {
    parent.gadgetsLogic();
  }

  @Override
  protected void geosearchLogic() {
    parent.geosearchLogic();
  }

  @Override
  protected void gettingstartedgetpagesLogic() {
    parent.gettingstartedgetpagesLogic();
  }

  @Override
  protected void globalallusersLogic() {
    parent.globalallusersLogic();
  }

  @Override
  protected void globalblocksLogic() {
    parent.globalblocksLogic();
  }

  @Override
  protected void globalgroupsLogic() {
    parent.globalgroupsLogic();
  }

  @Override
  protected void imageusageLogic() {
    parent.imageusageLogic();
  }

  @Override
  protected void iwbacklinksLogic() {
    parent.iwbacklinksLogic();
  }

  @Override
  protected void langbacklinksLogic() {
    parent.langbacklinksLogic();
  }

  @Override
  protected void logeventsLogic() {
    parent.logeventsLogic();
  }

  @Override
  protected void mmsitesLogic() {
    parent.mmsitesLogic();
  }

  @Override
  protected void oldreviewedpagesLogic() {
    parent.oldreviewedpagesLogic();
  }

  @Override
  protected void pagepropnamesLogic() {
    parent.pagepropnamesLogic();
  }

  @Override
  protected void pageswithpropLogic() {
    parent.pageswithpropLogic();
  }

  @Override
  protected void prefixsearchLogic() {
    parent.prefixsearchLogic();
  }

  @Override
  protected void protectedtitlesLogic() {
    parent.protectedtitlesLogic();
  }

  @Override
  protected void querypageLogic() {
    parent.querypageLogic();
  }

  @Override
  protected void randomLogic() {
    parent.randomLogic();
  }

  @Override
  protected void recentchangesLogic() {
    parent.recentchangesLogic();
  }

  @Override
  protected void searchLogic() {
    parent.searchLogic();
  }

  @Override
  protected void tagsLogic() {
    parent.tagsLogic();
  }

  @Override
  protected void usercontribsLogic() {
    parent.usercontribsLogic();
  }

  @Override
  protected void usersLogic() {
    parent.usersLogic();
  }

  @Override
  protected void watchlistLogic() {
    parent.watchlistLogic();
  }

  @Override
  protected void watchlistrawLogic() {
    parent.watchlistrawLogic();
  }

  @Override
  protected void wikigrokrandomLogic() {
    parent.wikigrokrandomLogic();
  }

  @Override
  protected void wikisetsLogic() {
    parent.wikisetsLogic();
  }
}
