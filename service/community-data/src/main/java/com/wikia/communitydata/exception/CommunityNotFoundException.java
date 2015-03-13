package com.wikia.communitydata.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class CommunityNotFoundException extends NotFoundException {
  private CommunityNotFoundException(Response response) {
    super(response);
  }

  public static final class Builder {
    private String domain;

    public Builder(String domain) {
      this.domain = domain;
    }

    public CommunityNotFoundException build() {
      String message = String.format("community %s does not exist", domain);

      Response response = Response
          .status(Response.Status.NOT_FOUND)
          .entity(new ErrorResponse(message))
          .build();

      return new CommunityNotFoundException(response);
    }
  }
}
