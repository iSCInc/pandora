package com.wikia.mwapi.fluent;


import com.wikia.mwapi.ApiResponse;

public interface OptionChoose {
  ApiResponse get();

  OptionChoose revisions();

  OptionChoose rvLimit(Integer limit);

  OptionChoose rvContent();
}
