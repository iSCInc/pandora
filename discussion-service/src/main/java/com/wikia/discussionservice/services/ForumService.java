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

  public static String getType() {
    return "forum";
  }

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

  public Optional<Forum> getForum(int siteId, int forumId, int offset, int limit) {
    Optional<Forum> forum = forumDAO.retrieveForum(siteId, forumId);
    
    if (forum.isPresent()) {
      List<ForumThread> threads = threadService.retrieveForumThreads(
          siteId, forumId, offset, limit);
      forum.get().setThreads(threads);
    }

    return forum;
  }

  public Optional<Forum> createForum(int siteId, int parentId, String forumName) {
    // TODO: Use redis
    return forumDAO.createForum(siteId, parentId, forumName);
  }
}
