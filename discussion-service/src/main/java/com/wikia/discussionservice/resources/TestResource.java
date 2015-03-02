package com.wikia.discussionservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.discussionservice.services.TestService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import io.dropwizard.jersey.params.IntParam;
import lombok.NonNull;

@Path("/")
@Produces(RepresentationFactory.HAL_JSON)
public class TestResource {

  @NonNull
  final private TestService testService;

  @Inject
  public TestResource(TestService testService) {
    this.testService = testService;
  }

  @GET
  @Path("/{siteId}/test/{key}")
  @Timed
  public String getValue(@NotNull @PathParam("siteId") IntParam siteId,
                                  @NotNull @PathParam("key") IntParam key) {

    String value = testService.getRedisValue(siteId.get(), testService.getType(), key.get());
    return value;
  }

  @POST
  @Path("/{siteId}/test/{key}")
  @Timed
  public String createTest(@NotNull @PathParam("siteId") IntParam siteId,
                            @NotNull @PathParam("key") IntParam key,
                                    @Context HttpServletRequest request) {
    String status;

    try {
      HashMap<String, Object> result =
          new ObjectMapper().readValue(
              new BufferedReader(new InputStreamReader(
                  request.getInputStream())), HashMap.class);

      String value = (String) result.getOrDefault("value", "default name");
      testService.setRedisValue(siteId.get(), TestService.getType(), key.get(), value);
      status = "Success!";

    } catch (IOException e) {
      status = "Failed: " + e.toString();
    }

    return status;
  }


}
