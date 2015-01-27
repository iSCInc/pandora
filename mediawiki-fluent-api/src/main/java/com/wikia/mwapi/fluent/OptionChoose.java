package com.wikia.mwapi.fluent;


import com.wikia.mwapi.domain.ApiResponse;

public interface OptionChoose {
  ApiResponse get();

  OptionChoose revisions();

  OptionChoose rvLimit(Integer limit);

  OptionChoose rvContent();
}
