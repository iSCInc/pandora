package com.wikia.mwapi.fluent;

public interface TitlesOrListChoose extends ListOption {

  QueryOption titles(String... titles);

  QueryOption allPages();

  QueryOption revIds(Long... revId);
}
