package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.core.EmptyMobileConfiguration;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.exceptions.ConfigurationNotFoundException;
import com.wikia.mobileconfig.exceptions.InvalidApplicationTagException;
import com.wikia.mobileconfig.exceptions.MobileConfigException;
import com.wikia.mobileconfig.gateway.AppsListService;
import com.wikia.mobileconfig.service.ConfigurationService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import io.dropwizard.jersey.caching.CacheControl;

@Path("/configurations/{platform}/apps/{app-tag}")
@Produces(RepresentationFactory.HAL_JSON)
public class MobileConfigResource {

  private final static String APP_TAG_VALIDATION_EXCEPTION_MESSAGE =
      "An exception occurred while validating application tag";

  private final static String GET_CONFIGURATION_EXCEPTION_MESSAGE =
      "An exception occurred while getting configuration";

  private final ConfigurationService appConfiguration;
  private final AppsListService appsList;

  public MobileConfigResource(ConfigurationService configuration, AppsListService list) {
    this.appConfiguration = configuration;
    this.appsList = list;
  }

  /**
   * GET /configurations/{platform}/apps/{app-tag}
   *
   * @return Representation
   */
  @GET
  @Timed
  @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.SECONDS)
  public Representation getMobileApplicationConfig(
      @PathParam("platform") String platform,
      @PathParam("app-tag") String appTag,
      @QueryParam("ui-lang") String uiLang,
      @QueryParam("content-lang") String contentLang
  ) throws MobileConfigException {
    Boolean isValidAppTag = isValidAppTag(platform, appTag);

    if (!isValidAppTag) {
      throw new InvalidApplicationTagException(appTag);
    }

    MobileConfiguration configuration = getConfiguration(platform, appTag, uiLang, contentLang);

    if (configuration.getModules().isEmpty()) {
      throw new ConfigurationNotFoundException(platform, appTag);
    }

    Representation rep = MobileConfigApplication.REPRESENTATION_FACTORY.newRepresentation(
        this.appConfiguration.createSelfUrl(platform, appTag)
    ).withBean(configuration);

    return rep;
  }

  private Boolean isValidAppTag(String platform, String appTag) {
    Boolean isValidAppTag;

    try {
      isValidAppTag = this.appsList.isValidAppTag(platform, appTag);
    } catch (IOException e) {
      isValidAppTag = false;
      MobileConfigApplication.LOGGER.info(APP_TAG_VALIDATION_EXCEPTION_MESSAGE, e);
    }

    return isValidAppTag;
  }

  private MobileConfiguration getConfiguration(String platform,
                                               String appTag,
                                               String uiLang,
                                               String contentLang) {
    MobileConfiguration configuration;

    try {
      configuration = this.appConfiguration.getConfiguration(platform, appTag, uiLang, contentLang);
    } catch (IOException e) {
      configuration = new EmptyMobileConfiguration();
      MobileConfigApplication.LOGGER.info(GET_CONFIGURATION_EXCEPTION_MESSAGE, e);
    }

    return configuration;
  }
}
