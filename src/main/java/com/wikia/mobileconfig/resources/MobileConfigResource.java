package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.core.MobileConfiguration;
import com.wikia.mobileconfig.service.ConfigurationService;

import javax.ws.rs.*;
import java.net.URISyntaxException;

@Path("/configurations/platform/{platform}/app/{appTag}")
@Produces(RepresentationFactory.HAL_JSON)
public class MobileConfigResource {

    private final ConfigurationService service;

    public MobileConfigResource(ConfigurationService service) {
        this.service = service;
    }

    /**
     * GET /configurations/platform/{platform}/app/{appTag}
     *
     * @param platform
     * @param appTag
     *
     * @return String
     */
    @GET
    @Timed
    public Representation getMobileApplicationConfig(
        @PathParam("platform") String platform,
        @PathParam("appTag") String appTag
    ) throws java.io.IOException, URISyntaxException {
        Representation rep = MobileConfigApplication.representationFactory.newRepresentation(
            this.service.createSelfUrl(platform, appTag)
        ).withBean(
            this.service.getConfiguration(platform, appTag)
        );

        return rep;
    }

}
