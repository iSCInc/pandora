package com.wikia.discussionservice.dao;

import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.domain.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.Optional;

import lombok.NonNull;
import redis.clients.jedis.Jedis;

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
  public Optional<Post> createPost(Jedis jedis, int siteId, int threadId, Post post) {
    int postId = POST_SEQUENCE++;

    post.setThreadId(threadId);
    post.setId(postId);
    return Optional.ofNullable(contentDAO.createContent(jedis, siteId, post));
  }

  public Optional<Post> getPost(Jedis jedis, int siteId, int postId) {
    return Optional.ofNullable(contentDAO.getContent(jedis, siteId, postId, Post.class));
  }

  public Optional<Post> deletePost(Jedis jedis, int siteId, int postId) {
    Optional<Post> deletedPost = getPost(jedis, siteId, postId);
    if (deletedPost.isPresent()) {
      contentDAO.deleteContent(jedis, siteId, postId, Post.class);
    }

    return deletedPost;
  }

}
