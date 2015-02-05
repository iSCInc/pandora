package com.wikia.mwapi.fluent.query;

import com.wikia.mwapi.enumtypes.SortTypeEnum;
import com.wikia.mwapi.enumtypes.query.properties.CMPropEnum;
import com.wikia.mwapi.enumtypes.query.properties.CMTypeEnum;
import com.wikia.mwapi.fluent.QueryOption;

public interface CategoryMembersOption extends QueryOption {

  CategoryMembersOption cmtitle(String title);

  CategoryMembersOption cmpageid(Long id);

  CategoryMembersOption cmprop(CMPropEnum... cmPropEnums);

  CategoryMembersOption cmnamespace(int... namespaces);

  CategoryMembersOption cmtype(CMTypeEnum... cmTypeEnums);

  CategoryMembersOption cmcontinue(String cmContinue);

  CategoryMembersOption cmlimit(int limit);

  CategoryMembersOption cmsort(SortTypeEnum sortTypeEnum);
}
