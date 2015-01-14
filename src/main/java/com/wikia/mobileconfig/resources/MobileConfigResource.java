package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

import com.wikia.mobileconfig.api.MobileApplicationConfig;
import com.wikia.mobileconfig.utils.MediaTypes;

import javax.ws.rs.*;
import java.net.URISyntaxException;

@Path("/configurations/platform/{platform}/app/{appTag}/version/{appVersion}")
@Produces(MediaTypes.HAL)
public class MobileConfigResource {

    /**
     * GET /configurations/platform/{platform}/app/{appTag}/version/{appVersion}
     *
     * @param platform
     * @param appTag
     * @param appVersion
     *
     * @return Representation
     * @throws java.io.IOException, WebApplicationException
     */
    @GET
    @Timed
    public MobileApplicationConfig getMobileApplicationConfig(
        @PathParam("platform") String platform,
        @PathParam("appTag") String appTag,
        @PathParam("appVersion") String appVersion
    ) throws java.io.IOException, URISyntaxException {
        return new MobileApplicationConfig(platform, appTag, appVersion);
    }

}
