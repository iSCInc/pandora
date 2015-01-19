package com.wikia.mobileconfig.resources;

import com.codahale.metrics.annotation.Timed;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import com.wikia.mobileconfig.MobileConfigApplication;
import com.wikia.mobileconfig.service.ConfigurationService;
import com.wikia.mobileconfig.service.ConfigurationsListService;

import javax.ws.rs.*;
import java.net.URISyntaxException;

@Path("/configurations/platform/{platform}/app/{appTag}")
@Produces(RepresentationFactory.HAL_JSON)
public class MobileConfigResource {

    private final ConfigurationService cfgService;
    private final ConfigurationsListService listService;

    public MobileConfigResource(ConfigurationService service, ConfigurationsListService listService) {
        this.cfgService = service;
        this.listService = listService;
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
        if( !this.listService.isValidAppTag(appTag) ) {
            throw new WebApplicationException(404);
        }

        Representation rep = MobileConfigApplication.representationFactory.newRepresentation(
            this.cfgService.createSelfUrl(platform, appTag)
        ).withBean(
            this.cfgService.getConfiguration(platform, appTag)
        );

        return rep;
    }

}
