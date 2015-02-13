package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriBuilder;

@Path("{wikia}/MercuryArticles")
@Produces(RepresentationFactory.HAL_JSON)
public class HalMercuryResource {

  private final ArticleService articleService;
  private final RepresentationFactory representationFactory;
  private final HalArticleResource articleResource;

  public HalMercuryResource(ArticleService articleService,
                            RepresentationFactory representationFactory) {
    this.articleService = articleService;
    this.representationFactory = representationFactory;
    articleResource = new HalArticleResource(articleService, representationFactory);
  }

  @GET
  @Path("/{title}")
  @Timed
  public Object getMercuryApiArticle(@PathParam("wikia") String wikia,
                                     @PathParam("title") String title) {
    UriBuilder uriBuilder = UriBuilder.fromResource(HalMercuryResource.class).path("{title}");
    Representation
        representation =
        representationFactory.newRepresentation(uriBuilder.build(wikia, title));

    Representation
        articleRepresentation =
        (Representation) articleResource.getArticle(wikia, title);

    representation.withRepresentation("article", articleRepresentation);

    Representation
        topContributorsRepresentation =
        HalMercuryMockResource.getTopContributors(wikia, title);

    representation.withRepresentation("topContributors", topContributorsRepresentation);

    Representation adsContextRepresentation = HalMercuryMockResource.getAdsContext(wikia, title);

    representation.withRepresentation("adsContext", adsContextRepresentation);

    return representation;
  }
}
