package com.wikia.discussionservice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.discussionservice.services.ForumService;

import javax.inject.Named;

public class DiscussionServiceModule implements Module {

  @Override
  public void configure(Binder binder) {

  }

  @Provides
  @Named("representationFactory")
  public RepresentationFactory provideRepresentationFactory() {
    return new StandardRepresentationFactory();
  }

  @Provides
  @Named("forumService")
  public ForumService provideForumService() {
    return new ForumService();
  }

}
