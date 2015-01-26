package com.wikia.pandora.resources;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.util.UriBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
    Representation
        representation =
        representationFactory.newRepresentation(UriBuilder.getSelfUri(wikia, title));

    Representation
        articleRepresentation =
        (Representation) articleResource.getArticleWithContent(wikia, title);
    representation.withRepresentation("article", articleRepresentation);
//    representation.withRepresentation()
//    Article article = articleService.getArticleByTitle(wikia, title);

//    MercuryArticleData data = MercuryArticleDataBuilder.aMercuryArticleData()
//        .withDetails(MercuryArticleDetailBuilder.aMercuryArticleDetail()
//                         .withTitle(article.getTitle())
//                         .withId(article.getId())
//                         .withNs(article.getNs())
//                         .build())
//        .build();
//    representation.withBean(data);

    return representation;
  }
}