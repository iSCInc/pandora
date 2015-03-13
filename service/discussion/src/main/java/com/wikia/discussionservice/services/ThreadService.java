package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.ThreadDAO;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import lombok.NonNull;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;
import java.util.Optional;

public class ThreadService {

  @NonNull
  private final PostService postService;

  @NonNull
  private final ThreadDAO threadDAO;

  @Inject
  public ThreadService(PostService postService, ThreadDAO threadDAO) {
    this.threadDAO = threadDAO;
    this.postService = postService;
  }

  public Optional<ForumThread> getForumThread(Jedis jedis, int siteId, int threadId) {
    return threadDAO.getForumThread(jedis, siteId, threadId, 0, 10);
  }

  public Optional<ForumThread> getForumThread(Jedis jedis, int siteId, int threadId, int offset, int limit) {
    return threadDAO.getForumThread(jedis, siteId, threadId, offset, limit);
  }
  
  public Optional<ForumThread> createThread(Jedis jedis, int siteId, int forumId, Post post) {
    return threadDAO.createThread(jedis, siteId, forumId, post);
  }

  public Optional<ForumThread> deleteThread(Jedis jedis, int siteId, int threadId) {
    return threadDAO.deleteThread(jedis, siteId, threadId);
  }

}