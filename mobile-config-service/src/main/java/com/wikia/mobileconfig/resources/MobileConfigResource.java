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
import com.wikia.mobileconfig.gateway.AppsListService;

import javax.ws.rs.*;

@Path("/configurations/platform/{platform}/app/{app-tag}")
@Produces(RepresentationFactory.HAL_JSON)
public class MobileConfigResource {

  private final ConfigurationService appConfiguration;
  private final AppsListService appsList;

  public MobileConfigResource(ConfigurationService configuration, AppsListService list) {
    this.appConfiguration = configuration;
    this.appsList = list;
  }

  /**
   * GET /configurations/platform/{platform}/app/{app-tag}
   *
   * @return Representation
   */
  @GET
  @Timed
  public Representation getMobileApplicationConfig(
      @PathParam("platform") String platform,
      @PathParam("app-tag") String appTag,
      @QueryParam("ui-lang") String uiLang,
      @QueryParam("content-lang") String contentLang
  ) throws java.io.IOException, MobileConfigException {

    if (!this.appsList.isValidAppTag(platform, appTag)) {
      throw new InvalidApplicationTagException(appTag);
    }

    MobileConfiguration configuration =
        this.appConfiguration.getConfiguration(platform, appTag, uiLang, contentLang);

    if (configuration.getModules().isEmpty()) {
      throw new ConfigurationNotFoundException(platform, appTag);
    }

    Representation rep = MobileConfigApplication.REPRESENTATION_FACTORY.newRepresentation(
        this.appConfiguration.createSelfUrl(platform, appTag)
    ).withBean(configuration);

    return rep;
  }
}
