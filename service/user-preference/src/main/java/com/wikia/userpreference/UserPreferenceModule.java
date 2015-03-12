package com.wikia.userpreference;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;
import com.wikia.userpreference.configuration.UserPreferenceConfiguration;
import com.wikia.userpreference.dao.FakeWikiCitiesDAO;
import com.wikia.userpreference.dao.WikiCitiesDAO;

public class UserPreferenceModule extends AbstractModule {

  @Override
  protected void configure() {
//    bind(ProvidesConsulConfiguration.class).to(UserPreferenceConfiguration.class);
//    binder().install(new ConsulModule());
  }

  @Provides
  @Named("wikiCitiesDAO")
  public WikiCitiesDAO provideWikiCitiesDAO() {
    Injector injector = Guice.createInjector(new UserPreferenceModule());
    return injector.getInstance(FakeWikiCitiesDAO.class);
  }

//  @Provides
//  @Named("userPreferenceService")
//  public UserPreferenceService provideUserPreferenceService() {
//    Injector injector = Guice.createInjector(new UserPreferenceModule());
//    return injector.getInstance(UserPreferenceService.class);
//
//  }

}
