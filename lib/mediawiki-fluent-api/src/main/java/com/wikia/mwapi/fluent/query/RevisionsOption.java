package com.wikia.mwapi.fluent.query;

import com.wikia.mwapi.enumtypes.SortDirectionByTimeEnum;
import com.wikia.mwapi.enumtypes.query.properties.RVContentFormatEnum;
import com.wikia.mwapi.enumtypes.query.properties.RVDiffEnum;
import com.wikia.mwapi.enumtypes.query.properties.RVPropEnum;
import com.wikia.mwapi.fluent.QueryOption;

import org.joda.time.DateTime;

public interface RevisionsOption extends QueryOption {

  RevisionsOption rvprop(RVPropEnum... rvPropsEnums);

  RevisionsOption rvlimit(int limit);

  RevisionsOption rvexpandtemplates();

  RevisionsOption rvgeneratexml();

  RevisionsOption rvparse();

  RevisionsOption rvsection(int section);

  RevisionsOption rvdiffto(RVDiffEnum diff);

  RevisionsOption rvdifftotext();

  RevisionsOption rvcontentformat(RVContentFormatEnum contentFormat);

  RevisionsOption rvstartid(int id);

  RevisionsOption rvendid(int id);

  RevisionsOption rvstart(DateTime dt);

  RevisionsOption rvend(DateTime dt);

  RevisionsOption rvdir(SortDirectionByTimeEnum direction);

  RevisionsOption rvuser(String userName);

  RevisionsOption rvexcludeuser(String userName);

  RevisionsOption rvtag(String tag);

  RevisionsOption rvcontinue(String cont);
}
