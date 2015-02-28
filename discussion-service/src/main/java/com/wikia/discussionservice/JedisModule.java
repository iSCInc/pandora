package com.wikia.discussionservice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

import com.wikia.discussionservice.mappers.DataStore;
import com.wikia.discussionservice.services.JedisService;

import javax.inject.Named;

/**
 * Created by armon on 2/27/15.
 */
public class JedisModule extends AbstractModule {

  @Override
  public void configure() {
    bind(DataStore.class).to(JedisService.class);
  }

  @Provides
  @Named("jedisService")
  public JedisService provideJedisService() {
    Injector injector = Guice.createInjector(new JedisModule());
    return injector.getInstance(JedisService.class);
  }

}
