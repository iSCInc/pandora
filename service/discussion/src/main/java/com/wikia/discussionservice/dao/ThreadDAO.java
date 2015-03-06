package com.wikia.discussionservice.dao;

import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.domain.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.Optional;

/**
 * THIS IS TEMPORARY THROWAWAY CODE
 */

public class ThreadDAO extends ContentDAO {

  private static int THREAD_SEQUENCE = 0;


  @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  public Optional<ForumThread> createThread(int siteId, int forumId, Post post) {
    int threadId = THREAD_SEQUENCE++;
    
    ForumThread forumThread = new ForumThread();
    forumThread.setId(threadId);
    forumThread.setForumId(forumId);
    forumThread.setPosts(new ArrayList<>(Arrays.asList(post)));
    forumThread.setLastPost(post);
    forumThread.setTitle(post.getTitle());

    // TODO: find the user or have user passed in 
    User user = new User();
    user.setId(post.getPosterId());
    user.setName("Made up user");
    forumThread.setThreadStarter(user);

    createContent(siteId, forumThread);

    return Optional.ofNullable(forumThread);
  }


  public Optional<ForumThread> getForumThread(int siteId, int threadId, int offset, int limit) {
    return Optional.ofNullable(getContent(siteId, threadId, ForumThread.class));
  }
}
