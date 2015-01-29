package com.wikia.mwapi.fluent;


import com.wikia.mwapi.domain.ApiResponse;

public interface OptionChoose {

  ApiResponse get();

  String url();

  OptionChoose revisions();
  
  PropChoose prop();

  RevisionOptionChoose rv();
}
