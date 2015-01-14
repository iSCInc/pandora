package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

import com.wikia.mobileconfig.api.MobileApplicationConfig;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import javax.ws.rs.*;
import java.net.URISyntaxException;

@Path("/configurations/platform/{platform}/app/{appTag}/version/{appVersion}")
@Produces(RepresentationFactory.HAL_JSON)
public class MobileConfigResource {

    /**
     * GET /configurations/platform/{platform}/app/{appTag}/version/{appVersion}
     *
     * @param platform
     * @param appTag
     * @param appVersion
     *
     * @return String
     */
    @GET
    @Timed
    public String getMobileApplicationConfig(
        @PathParam("platform") String platform,
        @PathParam("appTag") String appTag,
        @PathParam("appVersion") String appVersion
    ) throws java.io.IOException, URISyntaxException {
        MobileApplicationConfig cfg = new MobileApplicationConfig();
        RepresentationFactory representationFactory = new StandardRepresentationFactory();
        Representation rep = representationFactory.newRepresentation()
            .withLink("self", cfg.getSelfUrl(platform, appTag, appVersion))
            .withProperty("modules", cfg.getMockedModules());

        return rep.toString(RepresentationFactory.HAL_JSON);
    }

}
