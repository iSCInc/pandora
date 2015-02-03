package com.wikia.mwapi.decorator.query;

import com.wikia.mwapi.decorator.MWApiDecorator;
import com.wikia.mwapi.enumtypes.ListEnum;
import com.wikia.mwapi.enumtypes.PropEnum;
import com.wikia.mwapi.fluent.query.CategoriesOption;
import com.wikia.mwapi.fluent.QueryOption;
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
import com.wikia.mwapi.fluent.query.ImageInfoOption;
import com.wikia.mwapi.fluent.query.ImagesOption;
import com.wikia.mwapi.fluent.query.InfoOption;
import com.wikia.mwapi.fluent.query.IWLinksOption;
import com.wikia.mwapi.fluent.query.LangLinksOption;
import com.wikia.mwapi.fluent.query.LinksOption;
import com.wikia.mwapi.fluent.query.LinksHereOption;
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

public class MWApiQueryDecorator extends MWApiDecorator implements QueryOption {

  private MWApiQuery parent;

  public MWApiQueryDecorator(MWApiQuery parent) {
    super(parent);
    this.parent = parent;
  }

  @Override
  public QueryOption list(ListEnum... enums) {
    return parent.list(enums);
  }

  @Override
  public QueryOption prop(PropEnum... propEnums) {
    return parent.prop(propEnums);
  }

  @Override
  public CategoriesOption categories() {
    return parent.categories();
  }

  @Override
  public CategoryInfoOption categoryinfo() {
    return parent.categoryinfo();
  }

  @Override
  public ContributorsOption contributors() {
    return parent.contributors();
  }

  @Override
  public CoordinatesOption coordinates() {
    return parent.coordinates();
  }

  @Override
  public DeletedRevisionsOption deletedrevisions() {
    return parent.deletedrevisions();
  }

  @Override
  public DuplicateFilesOption duplicatefiles() {
    return parent.duplicatefiles();
  }

  @Override
  public ExtLinksOption extlinks() {
    return parent.extlinks();
  }

  @Override
  public ExtractsOption extracts() {
    return parent.extracts();
  }

  @Override
  public FileUsageOption fileusage() {
    return parent.fileusage();
  }

  @Override
  public FlaggedOption flagged() {
    return parent.flagged();
  }

  @Override
  public FlowInfoOption flowinfo() {
    return parent.flowinfo();
  }

  @Override
  public GlobalUsageOption globalusage() {
    return parent.globalusage();
  }

  @Override
  public ImageInfoOption imageinfo() {
    return parent.imageinfo();
  }

  @Override
  public ImagesOption images() {
    return parent.images();
  }

  @Override
  public InfoOption info() {
    return parent.info();
  }

  @Override
  public IWLinksOption iwlinks() {
    return parent.iwlinks();
  }

  @Override
  public LangLinksOption langlinks() {
    return parent.langlinks();
  }

  @Override
  public LinksOption links() {
    return parent.links();
  }

  @Override
  public LinksHereOption linkshere() {
    return parent.linkshere();
  }

  @Override
  public PageImagesOption pageimages() {
    return parent.pageimages();
  }

  @Override
  public PagePropsOption pageprops() {
    return parent.pageprops();
  }

  @Override
  public PageTermsOption pageterms() {
    return parent.pageterms();
  }

  @Override
  public RedirectsOption redirects() {
    return parent.redirects();
  }

  @Override
  public RevisionsOption revisions() {
    return parent.revisions();
  }

  @Override
  public StashImageInfoOption stashimageinfo() {
    return parent.stashimageinfo();
  }

  @Override
  public TemplatesOption templates() {
    return parent.templates();
  }

  @Override
  public TranscludedInOption transcludedin() {
    return parent.transcludedin();
  }

  @Override
  public TranscodeStatusOption transcodestatus() {
    return parent.transcodestatus();
  }

  @Override
  public VideoInfoOption videoinfo() {
    return parent.videoinfo();
  }
}
