package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.service.ConfigurationService;
import com.wikia.mobileconfig.service.AppsListService;

import javax.ws.rs.*;

import java.net.URISyntaxException;

@Path("/configurations/platform/{platform}/app/{appTag}")
@Produces(RepresentationFactory.HAL_JSON)
public class MobileConfigResource {

  private final ConfigurationService appConfiguration;
  private final AppsListService appsList;

  public MobileConfigResource(ConfigurationService configuration, AppsListService list) {
    this.appConfiguration = configuration;
    this.appsList = list;
  }

  /**
   * GET /configurations/platform/{platform}/app/{appTag}
   *
   * @return Representation
   */
  @GET
  @Timed
  public Representation getMobileApplicationConfig(
      @PathParam("platform") String platform,
      @PathParam("appTag") String appTag
  ) throws java.io.IOException, URISyntaxException {

    if (!this.appsList.isValidAppTag(appTag)) {
      //TODO: make it a cosher application/problem+json exception
      throw new WebApplicationException(404);
    }

    MobileConfiguration configuration = this.appConfiguration.getConfiguration(platform, appTag);

    if (configuration.getModules().isEmpty()) {
      //TODO: make it a cosher application/problem+json exception
      throw new WebApplicationException(404);
    }

    Representation rep = MobileConfigApplication.representationFactory.newRepresentation(
        this.appConfiguration.createSelfUrl(platform, appTag)
    ).withBean(configuration);

    return rep;
  }

}
