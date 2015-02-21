package com.wikia.discussionservice;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.discussionservice.services.ForumService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DiscussionServiceSpringConfiguration.class)
public class DiscussionServiceSpringConfiguration {

  @Bean
  public RepresentationFactory representationFactory() {
    return new StandardRepresentationFactory();
  }

  @Bean
  public ForumService forumService() {
    return new ForumService();
  }
}
