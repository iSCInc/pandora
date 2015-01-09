package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.wikia.pandora.api.Article;
import com.wikia.pandora.api.MediaTypes;
import com.wikia.pandora.gateway.MercuryGateway;
import com.wikia.pandora.service.ArticleService;

import javax.ws.rs.*;

@Path("/articles/{wikia}/{title}")
@Produces(MediaTypes.HAL)
public class ArticlesResource {

    private final ArticleService articleService;

    public ArticlesResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     *
     * GET /articles/<wikia>/<title>
     * FIXME: Is this the right way to handle the wikia? It's not the current convention,
     * but it's simple. Do we do any domain magic? Are there any other considerations?
     *
     * @param wikia
     * @param title
     * @return
     * @throws java.io.IOException, WebApplicationException
     */
    @GET
    @Timed
    public Article get(@PathParam("wikia") String wikia, @PathParam("title") String title) throws java.io.IOException {
        Optional<Article> article = this.articleService.lookup(wikia, title);

        if (!article.isPresent()) {
            // FIXME: this should be an HTTP error response according to the guidelines
            // see https://jersey.java.net/documentation/latest/representations.html#d0e5155
            // for a guide on how to do this. The current error is too vague.
            throw new WebApplicationException(404);
        }

        return article.get();
    }

}
