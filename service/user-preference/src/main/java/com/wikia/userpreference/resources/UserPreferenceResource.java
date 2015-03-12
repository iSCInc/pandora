package com.wikia.userpreference.resources;

import com.wikia.userpreference.dao.WikiCitiesDAO;
import com.wikia.userpreference.domain.UserPreferences;
import com.wikia.userpreference.domain.UserPreference;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Api(basePath = "/preferences", value = "User preferences",
    produces = "application/json", description = "User preference resources.")
@Path("/preferences")
@Produces("application/json")
public class UserPreferenceResource {

  private final WikiCitiesDAO wikiCitiesDAO;

  @Inject
  public UserPreferenceResource(WikiCitiesDAO wikiCitiesDAO) {
    this.wikiCitiesDAO = wikiCitiesDAO;
  }

  @GET
  @Path("/{userId}")
  @Produces("application/json")
  @ApiOperation(value = "Returns all the user preferences for a user", notes = "Returns the user id and all of their preferences",
      response = UserPreferences.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful retrieval of user preferences", response = UserPreferences.class)
      // There should be an easy way to do this with problem+json
      //, @ApiResponse(code = 404, message = "No user preferences found", response = ErrorResponse.class)
  })
  @Timed
  public Response getUserPreferences(@ApiParam(value = "The id of the user to list the preferences")
                                     @NotNull @PathParam("userId") Integer userId) {

    // FIXME: move this to a service
    UserPreferences userPreferences;
    Optional<ImmutableList<UserPreference>> preferences = this.wikiCitiesDAO.getUserPreferences(userId);

    if (!preferences.isPresent()) {
      return Response.status(404)
          .build();
    }

    userPreferences = UserPreferences.newBuilder()
        .userId(userId)
        .preferences(preferences.get())
        .build();

    return Response.ok(userPreferences)
        .build();
  }
}
