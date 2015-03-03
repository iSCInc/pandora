package com.wikia.mwapi.fluent.query;

import com.wikia.mwapi.domain.ApiResponse;

public interface MethodOption {

  ApiResponse get();

  ApiResponse post();

  String url();
}
