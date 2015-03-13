package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.ForumDAO;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.domain.ForumThread;
import lombok.NonNull;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Forum Service
 * Responsible for handling all actions around
 * the forum domain.
 */
public class ForumService {

  @NonNull
  private final ThreadService threadService;

  @NonNull
  private final ForumDAO forumDAO;

  @Inject
  public ForumService(ThreadService threadService, ForumDAO forumDAO) {
    this.threadService = threadService;
    this.forumDAO = forumDAO;
  }

  /**
   * This just creates a site=1 root forum
   * If another site needs a root, it gets created on the fly
   * @param jedis Jedis
   */
  public void init(Jedis jedis) {
    if (!forumDAO.retrieveForum(jedis, forumDAO.DEFAULT_SITE_ID, forumDAO.ROOT_ID).isPresent()) {
      forumDAO.getContentDAO().createContent(
          jedis,
          forumDAO.DEFAULT_SITE_ID,
          new Forum(
              forumDAO.ROOT_ID,
              forumDAO.PARENT_ROOT_ID,
              "Root",
              new ArrayList<>(),
              new ArrayList<>()
          )
      );
    }
  }

  public Optional<ForumRoot> getForums(Jedis jedis, int siteId) {
    List<Forum> forumList = forumDAO.retrieveForums(jedis, siteId);
    ForumRoot forumRoot = new ForumRoot();
    forumRoot.setForums(forumList);
    forumRoot.setSiteId(siteId);
    return Optional.of(forumRoot);
  }

  public Optional<Forum> getForum(Jedis jedis, int siteId, int forumId) {
    return getForum(jedis, siteId, forumId, 0, 10);
  }
  
  public Optional<Forum> getForum(Jedis jedis, int siteId, int forumId, int offset, int limit) {
    return forumDAO.retrieveForum(jedis, siteId, forumId);
  }

  public Optional<Forum> createForum(Jedis jedis, int siteId, Forum forum) {
    return forumDAO.createForum(jedis, siteId, forum);
  }

  public Optional<Forum> deleteForum(Jedis jedis, int siteId, int forumId) {
    return forumDAO.deleteForum(jedis, siteId, forumId);
  }

  public Optional<Forum> updateForum(Jedis jedis, int siteId, Forum forum) {
    Optional<Forum> updatedForum = forumDAO.updateForum(jedis, siteId, forum);
    
    if (updatedForum.isPresent()) {
      return updatedForum;
    }
    
    updatedForum = createForum(jedis, siteId, forum);
    return updatedForum;
  }
}
