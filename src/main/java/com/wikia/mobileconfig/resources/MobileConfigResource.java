package com.wikia.mobileconfig.resources;

// what about com.wikia.pandora.api.MediaTypes?
import com.wikia.mobileconfig.api.MediaTypes;

import javax.ws.rs.*;

@Path("/configuration/{apiVersion}/{platform}/{appTag}/{version}")
@Produces(MediaTypes.HAL)
public class MobileConfigResource {
}
