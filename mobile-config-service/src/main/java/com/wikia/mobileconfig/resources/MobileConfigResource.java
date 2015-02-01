package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.exceptions.ConfigurationNotFoundException;
import com.wikia.mobileconfig.exceptions.InvalidApplicationTagException;
import com.wikia.mobileconfig.exceptions.MobileConfigException;
import com.wikia.mobileconfig.service.ConfigurationService;
import com.wikia.mobileconfig.service.AppsListService;

import javax.ws.rs.*;

import java.io.IOException;

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
  ) throws IOException, MobileConfigException {

    if (!this.appsList.isValidAppTag(appTag)) {
      throw new InvalidApplicationTagException(appTag);
    }

    MobileConfiguration configuration = this.appConfiguration.getConfiguration(platform, appTag);

    if (configuration.getModules().isEmpty()) {
      throw new ConfigurationNotFoundException(platform, appTag);
    }

    Representation rep = MobileConfigApplication.representationFactory.newRepresentation(
        this.appConfiguration.createSelfUrl(platform, appTag)
    ).withBean(configuration);

    return rep;
  }

}
