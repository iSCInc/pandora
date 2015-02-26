package com.wikia.discussionservice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.discussionservice.mappers.*;
import com.wikia.discussionservice.services.ForumService;

import javax.inject.Named;

public class DiscussionServiceModule extends AbstractModule {

  @Override
  public void configure() {
    bind(ForumRepresentationMapper.class).to(HALForumRepresentationMapper.class);
    bind(ThreadRepresentationMapper.class).to(HALThreadRepresentationMapper.class);
    bind(PostRepresentationMapper.class).to(HALPostRepresentationMapper.class);
    bind(RepresentationFactory.class).to(StandardRepresentationFactory.class);
  }

  @Provides
  @Named("forumService")
  public ForumService provideForumService() {
    Injector injector = Guice.createInjector(new DiscussionServiceModule());
    return injector.getInstance(ForumService.class);
  }

}
