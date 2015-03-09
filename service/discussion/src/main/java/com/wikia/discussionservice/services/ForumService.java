package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.ForumDAO;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.domain.ForumThread;
import lombok.NonNull;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Forum Service
 * Responsible for handling all actions around
 * the forum domain.
 */
public class ForumService extends ContentService {

  @NonNull
  private final ThreadService threadService;

  @NonNull
  private final ForumDAO forumDAO;

  @Inject
  public ForumService(ThreadService threadService, ForumDAO forumDAO) {
    super();
    this.threadService = threadService;
    this.forumDAO = forumDAO;
  }

  public Optional<ForumRoot> getForums(int siteId) {
    List<Forum> forumList = forumDAO.retrieveForums(siteId);
    ForumRoot forumRoot = new ForumRoot();
    forumRoot.setForums(forumList);
    forumRoot.setSiteId(siteId);
    return Optional.of(forumRoot);
  }

  public Optional<Forum> getForum(int siteId, int forumId) {
    return getForum(siteId, forumId, 0, 10);
  }
  
  public Optional<Forum> getForum(int siteId, int forumId, int offset, int limit) {
    return forumDAO.retrieveForum(siteId, forumId);
  }

  public Optional<Forum> createForum(int siteId, Forum forum) {
    return forumDAO.createForum(siteId, forum);
  }

  public Optional<Forum> deleteForum(int siteId, int forumId) {
    return forumDAO.deleteForum(siteId, forumId);
  }

  public Optional<Forum> updateForum(int siteId, Forum forum) {
    Optional<Forum> updatedForum = forumDAO.updateForum(siteId, forum);
    
    if (updatedForum.isPresent()) {
      return updatedForum;
    }
    
    updatedForum = createForum(siteId, forum);
    return updatedForum;
  }
}
