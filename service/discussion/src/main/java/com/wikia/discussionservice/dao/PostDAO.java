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

public class PostDAO {

  private static int POST_SEQUENCE = 0;

  @NonNull
  ContentDAO contentDAO;

  @Inject
  public PostDAO(ContentDAO contentDAO) {
    this.contentDAO = contentDAO;
  }

  @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  public Optional<Post> createPost(int siteId, int threadId, Post post) {
    int postId = POST_SEQUENCE++;

    post.setThreadId(threadId);
    post.setId(postId);
    return Optional.ofNullable(contentDAO.createContent(siteId, post));
  }

  public Optional<Post> getPost(int siteId, int postId) {
    return Optional.ofNullable(contentDAO.getContent(siteId, postId, Post.class));
  }

  public Optional<Post> deletePost(int siteId, int postId) {
    Optional<Post> deletedPost = getPost(siteId, postId);
    if (deletedPost.isPresent()) {
      contentDAO.deleteContent(siteId, postId, Post.class);
    }

    return deletedPost;
  }

}
