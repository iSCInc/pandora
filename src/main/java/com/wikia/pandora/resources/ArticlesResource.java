package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.wikia.pandora.api.Article;
import com.wikia.pandora.api.MediaTypes;
import com.wikia.pandora.gateway.MercuryGateway;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Map;

@Path("/articles/{wikia}/{title}")
@Produces(MediaTypes.HAL)
public class ArticlesResource {

    private final MercuryGateway mercuryGateway;

    public ArticlesResource(MercuryGateway mercuryGateway) {
        this.mercuryGateway = mercuryGateway;
    }

    /**
     *
     * GET /articles/<wikia>/<title>
     *
     * @param wikia
     * @param title
     * @return
     * @throws java.io.IOException
     */
    @GET
    @Timed
    public Article get(@PathParam("wikia") String wikia, @PathParam("title") String title) throws java.io.IOException {
        // TODO: move this to a service
        Map<String, Object> responseMap = this.mercuryGateway.getArticle(wikia, title);
        Map<String, Object> data = (Map<String, Object>)responseMap.get("data");
        Map<String, Object> details = (Map<String, Object>)data.get("details");
        Map<String, Object> article = (Map<String, Object>)data.get("article");
        return new Article.Builder()
                .id((Integer)details.get("id"))
                .title((String)details.get("title"))
                .content((String)article.get("content"))
                .build();
    }

}
