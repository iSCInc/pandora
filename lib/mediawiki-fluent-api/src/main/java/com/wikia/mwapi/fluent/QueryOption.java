package com.wikia.mwapi.fluent;

import com.wikia.mwapi.enumtypes.ListEnum;
import com.wikia.mwapi.enumtypes.PropEnum;
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
import com.wikia.mwapi.fluent.query.MethodOption;
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

public interface QueryOption extends MethodOption {

  QueryOption list(ListEnum... enums);

  QueryOption prop(PropEnum... propEnums);

  CategoriesOption categories();

  CategoryInfoOption categoryinfo();

  ContributorsOption contributors();

  CoordinatesOption coordinates();

  DeletedRevisionsOption deletedrevisions();

  DuplicateFilesOption duplicatefiles();

  ExtLinksOption extlinks();

  ExtractsOption extracts();

  FileUsageOption fileusage();

  FlaggedOption flagged();

  FlowInfoOption flowinfo();

  GlobalUsageOption globalusage();

  ImageInfoOption imageinfo();

  ImagesOption images();

  InfoOption info();

  IWLinksOption iwlinks();

  LangLinksOption langlinks();

  LinksOption links();

  LinksHereOption linkshere();

  PageImagesOption pageimages();

  PagePropsOption pageprops();

  PageTermsOption pageterms();

  RedirectsOption redirects();

  RevisionsOption revisions();

  StashImageInfoOption stashimageinfo();

  TemplatesOption templates();

  TranscludedInOption transcludedin();

  TranscodeStatusOption transcodestatus();

  VideoInfoOption videoinfo();

  CategoryMembersOption categorymembers();
}
