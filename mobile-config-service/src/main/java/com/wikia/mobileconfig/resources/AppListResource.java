package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.core.AppList;
import com.wikia.mobileconfig.exceptions.MobileConfigException;
import com.wikia.mobileconfig.service.AppsListService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/appList/platform/{platform}/")
@Produces(RepresentationFactory.HAL_JSON)
public class AppListResource {

  private final AppsListService appsList;

  private final static String APP_LIST_URL_FORMAT = "/appList/platform/%s";

  public AppListResource(AppsListService list) {
    this.appsList = list;
  }

  public String createSelfUrl(String platform) {
    return String.format(APP_LIST_URL_FORMAT, platform);
  }

  /**
   * GET /appList/platform/{platform}
   *
   * @return Representation
   */
  @GET
  @Timed
  public Representation getMobileApplicationList(
      @PathParam("platform") String platform
  ) throws java.io.IOException, MobileConfigException {

    AppList bean = new AppList(this.appsList.getAppList(platform));

    Representation rep = MobileConfigApplication.representationFactory.newRepresentation(
        this.createSelfUrl(platform)
    ).withBean(bean);

    return rep;
  }
}
