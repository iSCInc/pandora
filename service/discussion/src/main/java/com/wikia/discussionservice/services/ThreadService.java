package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.ThreadDAO;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import lombok.NonNull;

import javax.inject.Inject;
import java.util.*;

public class ThreadService {

  private static Map<Integer, List<ForumThread>> FORUM_THREADS = new HashMap<>();
  
  @NonNull
  private final PostService postService;

  @NonNull
  private final ThreadDAO threadDAO;

  @Inject
  public ThreadService(PostService postService, ThreadDAO threadDAO) {
    this.threadDAO = threadDAO;
    this.postService = postService;
  }

  public Optional<ForumThread> getForumThread(int siteId, int threadId) {
    Optional<ForumThread> forumThread = threadDAO.getForumThread(siteId, threadId, 0, 10);
    return forumThread;
  }

  public Optional<ForumThread> getForumThread(int siteId, int threadId, int offset, int limit) {
    Optional<ForumThread> forumThread = threadDAO.getForumThread(siteId, threadId, offset, limit);
    return forumThread;
  }
  
  public Optional<ForumThread> createThread(int siteId, int forumId, Post post) {
    Optional<ForumThread> createdForumThread = threadDAO.createThread(siteId, forumId, post);
    return createdForumThread;
  }
}