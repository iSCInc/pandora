package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.wikia.pandora.api.Article;
import com.wikia.pandora.api.MediaTypes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/articles/{title}")
@Produces(MediaTypes.HAL)
public class ArticlesResource {

    @GET
    @Timed
    public Article get(@PathParam("title") String title) throws java.io.IOException {
        return new Article.Builder()
                .id(10)
                .title(title)
                .content(String.format("%s content", title))
                .build();
    }

}
