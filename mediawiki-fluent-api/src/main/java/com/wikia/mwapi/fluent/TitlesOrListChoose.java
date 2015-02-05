package com.wikia.mwapi.fluent;

import com.wikia.mwapi.fluent.query.CategoryMembersOption;

public interface TitlesOrListChoose {

  QueryOption titles(String... titles);

  QueryOption allPages();

  CategoryMembersOption categorymembers();

}
