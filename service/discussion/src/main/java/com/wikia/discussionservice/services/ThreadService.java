package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.ThreadDAO;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import lombok.NonNull;

import javax.inject.Inject;
import java.util.*;
import java.util.Optional;

public class ThreadService extends ContentService {

  @NonNull
  private final PostService postService;

  @NonNull
  private final ThreadDAO threadDAO;

  @Inject
  public ThreadService(PostService postService, ThreadDAO threadDAO) {
    super();
    this.threadDAO = threadDAO;
    this.postService = postService;
  }

  public Optional<ForumThread> getForumThread(int siteId, int threadId) {
    return threadDAO.getForumThread(siteId, threadId, 0, 10);
  }

  public Optional<ForumThread> getForumThread(int siteId, int threadId, int offset, int limit) {
    return threadDAO.getForumThread(siteId, threadId, offset, limit);
  }
  
  public Optional<ForumThread> createThread(int siteId, int forumId, Post post) {
    return threadDAO.createThread(siteId, forumId, post);
  }

  public Optional<ForumThread> deleteThread(int siteId, int threadId) {
    return threadDAO.deleteThread(siteId, threadId);
  }

}