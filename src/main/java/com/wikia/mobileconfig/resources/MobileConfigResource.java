package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

// TODO: what about com.wikia.pandora.api.MediaTypes?
import com.wikia.mobileconfig.api.MediaTypes;
import com.wikia.mobileconfig.api.MobileApplicationConfig;

import javax.ws.rs.*;

@Path("/configurations/platform/{platform}/app/{appTag}/version/{appVersion}")
@Produces(MediaTypes.HAL)
public class MobileConfigResource {
    /**
     *
     * GET /configurations/platform/{platform}/app/{appTag}/version/{appVersion}
     *
     * @param platform
     * @param appTag
     * @param appVersion
     *
     * @return MobileApplicationConfig
     * @throws java.io.IOException, WebApplicationException
     */
    @GET
    @Timed
    public MobileApplicationConfig get(
            @PathParam("platform") String platform,
            @PathParam("appTag") String appTag,
            @PathParam("appVersion") String appVersion
    ) throws java.io.IOException {
        MobileApplicationConfig cfg = new MobileApplicationConfig(
                platform,
                appTag,
                appVersion
        );

        return cfg;
    }
}
