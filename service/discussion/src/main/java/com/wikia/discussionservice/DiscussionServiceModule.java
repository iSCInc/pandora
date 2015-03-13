package com.wikia.discussionservice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.discussionservice.configuration.DiscussionServiceConfiguration;
import com.wikia.discussionservice.mappers.*;
import com.wikia.discussionservice.services.ForumService;
import com.wikia.discussionservice.services.PostService;
import com.wikia.discussionservice.services.ThreadService;
import com.wikia.discussionservice.services.JedisService;
import com.wikia.discussionservice.dao.ContentDAO;
import com.wikia.dropwizard.consul.bundle.ConsulConfiguration;
import com.wikia.dropwizard.consul.bundle.ConsulModule;
import com.wikia.dropwizard.consul.bundle.ProvidesConsulConfiguration;

import javax.inject.Named;

public class DiscussionServiceModule extends AbstractModule {

  @Override
  public void configure() {
    bind(ForumRepresentationMapper.class).to(HALForumRepresentationMapper.class);
    bind(ThreadRepresentationMapper.class).to(HALThreadRepresentationMapper.class);
    bind(PostRepresentationMapper.class).to(HALPostRepresentationMapper.class);
    bind(RepresentationFactory.class).to(StandardRepresentationFactory.class);
    bind(ProvidesConsulConfiguration.class).to(DiscussionServiceConfiguration.class);
    binder().install(new ConsulModule());
//    bindConstant().annotatedWith(Names.named("port"));
  }

  @Provides
  @Named("forumService")
  public ForumService provideForumService() {
    Injector injector = Guice.createInjector(new DiscussionServiceModule());
    return injector.getInstance(ForumService.class);
  }

  @Provides
  @Named("postService")
  public PostService providePostService() {
    Injector injector = Guice.createInjector(new DiscussionServiceModule());
    return injector.getInstance(PostService.class);
  }

  @Provides
  @Named("threadService")
  public ThreadService provideThreadService() {
    Injector injector = Guice.createInjector(new DiscussionServiceModule());
    return injector.getInstance(ThreadService.class);
  }

//  @Provides
//  @Named("jedisHost")
//  String getJedisHost() {
//    return "localhost";
//  }
//
//  @Provides
//  @Named("jedisPort")
//  String getJedisPort() {
//    return "6379";
//  }

//  @Provides
//  @Named("jedisService")
//  public JedisService provideJedisService(
////      @Named("jedisHost") String jedisHost,
////      @Named("jedisPort") String jedisPort
//  ) {
////    return new JedisService(jedisHost, jedisPort);
////  public JedisService provideJedisService(Provider<DiscussionServiceConfiguration> config) {
////    DiscussionServiceConfiguration c = config.get();
////    return c.getRedis();
//    Injector injector = Guice.createInjector(new DiscussionServiceModule());
//    return injector.getInstance(JedisService.class);
//  }

//  @Provides
//  public ContentDAO provideContentDAO(
//      @Named("jedisService") JedisService jedisService) {
//     return new ContentDAO(jedisService);
//  }
//  @Provides
//  public ContentDAO provideContentDAO() {
//    Injector injector = Guice.createInjector(new DiscussionServiceModule());
//    return injector.getInstance(ContentDAO.class);
////     return new ContentDAO(jedis);
//  }
}
