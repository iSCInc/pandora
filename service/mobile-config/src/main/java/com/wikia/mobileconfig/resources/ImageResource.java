package com.wikia.mobileconfig.resources;

import com.wikia.mobileconfig.exceptions.ImageNotFoundException;
import com.wikia.mobileconfig.service.ImageService;

import com.codahale.metrics.annotation.Timed;
import com.google.common.io.Files;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/images/{filename}")
public class ImageResource {

    private final ImageService imageService;

    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * GET /image/{id}
     *
     * @return image
     */
    @GET
    @Timed
    public Response getImage(
        @PathParam("filename") String filename
    ) throws java.io.IOException, URISyntaxException {

        Optional<byte[]> imageBytes = this.imageService.getImage(filename);

        if (!imageBytes.isPresent()) {
            throw new ImageNotFoundException(filename);
        }

        String contentType = "image/" + Files.getFileExtension(filename);
        return Response.ok(imageBytes.get(), MediaType.valueOf(contentType)).build();
    }
}