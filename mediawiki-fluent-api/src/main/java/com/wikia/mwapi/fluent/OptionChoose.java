package com.wikia.mwapi.fluent;


import com.wikia.mwapi.domain.ApiResponse;

public interface OptionChoose {

  ApiResponse get();

  PropChoose prop();

  RevisionOptionChoose rv();
}
