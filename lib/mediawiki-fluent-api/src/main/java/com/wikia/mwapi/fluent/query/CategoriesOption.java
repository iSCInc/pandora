package com.wikia.mwapi.fluent.query;

import com.wikia.mwapi.enumtypes.SortDirectionEnum;
import com.wikia.mwapi.enumtypes.query.properties.CLPropEnum;
import com.wikia.mwapi.enumtypes.query.properties.CLShowEnum;
import com.wikia.mwapi.fluent.QueryOption;

public interface CategoriesOption extends QueryOption {

  CategoriesOption clprop(CLPropEnum... clPropEnums);

  CategoriesOption clshow(CLShowEnum... clShowEnums);

  CategoriesOption cllimit(int limit);

  CategoriesOption clcontinue(String clContinue);

  CategoriesOption clcategories(String... categoryfilter);

  CategoriesOption cldir(SortDirectionEnum directionEnum);
}
