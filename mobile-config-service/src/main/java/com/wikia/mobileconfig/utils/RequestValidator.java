package com.wikia.mobileconfig.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Common tools for request validation
 */
public class RequestValidator {

    //TODO should be moved to core utils to enable consistent validation around pandora?
    public static void validate(boolean isOk, String message) {

        validate(isOk, message, Response.Status.BAD_REQUEST);
    }

    //TODO should be moved to core utils to enable consistent validation around pandora?
    public static void validate(boolean isOk, String message, Response.Status status) {

        if (isOk) {
            return;
        }

        throw new WebApplicationException(
                Response.status(status)
                        .entity(message)
                        .type(MediaType.TEXT_PLAIN)
                        .build());
    }
}
