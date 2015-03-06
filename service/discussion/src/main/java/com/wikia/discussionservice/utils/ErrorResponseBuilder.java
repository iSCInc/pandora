package com.wikia.discussionservice.utils;

import com.wikia.discussionservice.domain.ErrorResponse;

import javax.ws.rs.core.Response;

public class ErrorResponseBuilder {

  public static Response buildErrorResponse(int internalCode, String message, String moreInfo, 
                                            Response.Status httpStatus) {
    ErrorResponse error = new ErrorResponse();
    // internal error code
    error.setCode(internalCode);
    error.setStatus(httpStatus.getStatusCode());
    error.setMessage(message);
    error.setMoreInfo(moreInfo);

    return Response.status(httpStatus)
        .entity(error)
        .build();
  }
}
