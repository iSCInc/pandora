package com.wikia.pandora.resources.halbuilder;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.jaxrs.JaxRsHalBuilderSupport;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.pandora.resources.ArticleResource;
import com.wikia.pandora.resources.ResourceFactory;
import com.wikia.pandora.service.ArticleService;
import com.wikia.pandora.service.ServiceFactory;

import io.dropwizard.jersey.setup.JerseyEnvironment;

public class HalResourceFactory extends ResourceFactory {

  private final RepresentationFactory representationFactory;

  public HalResourceFactory(ServiceFactory serviceFactory) {
    super(serviceFactory);
    representationFactory = new StandardRepresentationFactory();
  }

  @Override
  public void registerResourcesInJersey(JerseyEnvironment jersey) {
    super.registerResourcesInJersey(jersey);
    jersey.register(JaxRsHalBuilderSupport.class);
  }

  @Override
  protected ArticleResource getArticleResource() {
    ArticleService articleService = getServiceFactory().createArticleService();
    return new HalArticleResource(articleService, representationFactory);

  }
}
