package com.wikia.discussionservice.dao;

import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.domain.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.Optional;

import com.wikia.discussionservice.services.JedisService;
import lombok.NonNull;

import javax.inject.Inject;

/**
 * THIS IS TEMPORARY THROWAWAY CODE
 */

public class ThreadDAO {

  private static int THREAD_SEQUENCE = 0;

  @NonNull
  ContentDAO contentDAO;

  @Inject
  public ThreadDAO(ContentDAO contentDAO) {
    this.contentDAO = contentDAO;
  }

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

    contentDAO.createContent(siteId, forumThread);

    return Optional.ofNullable(forumThread);
  }


  public Optional<ForumThread> getForumThread(int siteId, int threadId, int offset, int limit) {
    return Optional.ofNullable(contentDAO.getContent(siteId, threadId, ForumThread.class));
  }

  public Optional<ForumThread> deleteThread(int siteId, int threadId) {
    Optional<ForumThread> deletedThread = getForumThread(siteId, threadId, 0, 1);
    if (deletedThread.isPresent()) {
      contentDAO.deleteContent(siteId, threadId, ForumThread.class);
    }

    return deletedThread;
  }

}