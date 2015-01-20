package com.wikia.pandora.resources;


import com.wikia.pandora.service.ServiceFactory;


import io.dropwizard.jersey.setup.JerseyEnvironment;

public abstract class ResourceFactory {

  private final ServiceFactory serviceFactory;

  public ResourceFactory(ServiceFactory serviceFactory) {
    this.serviceFactory = serviceFactory;
  }

  protected abstract ArticleResource getArticleResource();

  public void registerResourcesInJersey(JerseyEnvironment jersey) {
    jersey.register(getArticleResource());
  }

  public ServiceFactory getServiceFactory() {
    return serviceFactory;
  }
}
