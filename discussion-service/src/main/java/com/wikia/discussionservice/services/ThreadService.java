package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.ThreadDAO;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.domain.User;
import lombok.NonNull;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ThreadService extends ContentService {

  private static Map<Integer, List<ForumThread>> FORUM_THREADS = new HashMap<>();
  
  @NonNull
  private final PostService postService;

  @NonNull
  private final ThreadDAO threadDAO;

  @Inject
  public ThreadService(PostService postService) {
    super();
    this.threadDAO = threadDAO;
    this.postService = postService;
  }

  public static String getType() {
    return "thread";
  }

  public Optional<ForumThread> getForumThread(int siteId, int threadId, int offset, int limit) {
    Optional<ForumThread> forumThread = threadDAO.getForumThread(siteId, threadId, offset, limit);
    return forumThread;
  }

  public List<ForumThread> retrieveForumThreads(int siteId, int forumId, int offset, int limit) {
    if (!FORUM_THREADS.containsKey(forumId)) {
      FORUM_THREADS.put(forumId, new ArrayList<>());
    }
    
    List<ForumThread> forumThreads = FORUM_THREADS.get(forumId);
    
    if (forumThreads.size() == 0 || forumThreads.size() <= limit) {
      return forumThreads;
    }
    
    try {
      return forumThreads.subList(offset*limit, offset*limit+limit);
    } catch(IndexOutOfBoundsException ioobe) {
      return forumThreads.subList(offset*limit, forumThreads.size()-1);
    }
  }
  
  public Optional<ForumThread> createThread(int siteId, int forumId, Post post) {
    Optional<ForumThread> createdForumThread = threadDAO.createThread(siteId, forumId, post);
    return createdForumThread;
  }
}