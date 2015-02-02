package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.service.ConfigurationService;
import com.wikia.mobileconfig.service.AppsListService;
import com.wikia.mobileconfig.utils.RequestValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.net.URISyntaxException;

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
  ) throws java.io.IOException, URISyntaxException {

    RequestValidator.validate(this.appsList.isValidAppTag(appTag), "Invalid app-tag provided", Response.Status.NOT_FOUND);
    //RequestValidator.validate(uiLang.length() == 2, "ui-lang has invalid length");
    //RequestValidator.validate(contentLang.length() == 2, "content-lang has invalid length");

    MobileConfiguration configuration =
        this.appConfiguration.getConfiguration(platform, appTag, uiLang, contentLang);

    RequestValidator.validate(!configuration.getModules().isEmpty(), "No Modules found",
                              Response.Status.NOT_FOUND);

    Representation rep = MobileConfigApplication.representationFactory.newRepresentation(
        this.appConfiguration.createSelfUrl(platform, appTag)
    ).withBean(configuration);

    return rep;
  }
}
