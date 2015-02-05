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
}
