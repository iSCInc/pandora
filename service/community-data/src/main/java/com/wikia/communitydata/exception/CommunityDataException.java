package com.wikia.communitydata.exception;

import static net.logstash.logback.marker.Markers.appendEntries;

import com.google.common.collect.ImmutableMap;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CommunityDataException extends WebApplicationException {
  private String domain;
  private Exception exception;

  private CommunityDataException(Response response, String domain, Exception exception) {
    super(response);
    this.domain = domain;
    this.exception = exception;

    log();
  }

  private void log() {
    LoggerFactory.getLogger(this.getClass()).error(
        appendEntries(
            new ImmutableMap.Builder<String, String>()
                .put("domain", domain)
                .build()
        ),
        "error fetching community data",
        exception
    );
  }

  public static final class Builder {
    private String domain;
    private Exception exception;

    public Builder setDomain(String domain) {
      this.domain = domain;
      return this;
    }

    public Builder setException(Exception e) {
      this.exception = e;
      return this;
    }

    public CommunityDataException build() {
      String message = String.format("error fetching data for %s", domain);

      Response response = Response
          .status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(new ErrorResponse(message))
          .build();

      return new CommunityDataException(response, domain, exception);
    }
  }
}
