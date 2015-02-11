package com.wikia.mwapi.fluent.query;

import com.wikia.mwapi.enumtypes.SortDirectionEnum;
import com.wikia.mwapi.enumtypes.query.properties.ACPropEnum;
import com.wikia.mwapi.fluent.QueryOption;

public interface AllCategoriesOption extends QueryOption {

  AllCategoriesOption acfrom(String acFrom);

  AllCategoriesOption acto(String acTo);

  AllCategoriesOption accontinue(String acContinue);

  AllCategoriesOption acprefix(String acPrefix);

  AllCategoriesOption acdir(SortDirectionEnum acDir);

  AllCategoriesOption acmin(int minArticles);

  AllCategoriesOption acmax(int maxArticles);

  AllCategoriesOption aclimit(int acLimit);

  AllCategoriesOption acprop(ACPropEnum... prop);


}
