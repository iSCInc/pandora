package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.PostDAO;
import com.wikia.discussionservice.domain.Post;
import lombok.NonNull;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;

import java.util.Optional;

public class PostService {

  @NonNull
  private final PostDAO postDAO;

  @Inject
  public PostService(PostDAO postDAO) {
    this.postDAO = postDAO;
  }

  public Optional<Post> getPost(Jedis jedis, int siteId, int postId) {
    return postDAO.getPost(jedis, siteId, postId);
  }

  public Optional<Post> createPost(Jedis jedis, int siteId, int threadId, Post post) {
    return postDAO.createPost(jedis, siteId, threadId, post);
  }

  public Optional<Post> deletePost(Jedis jedis, int siteId, int postId) {
    return postDAO.deletePost(jedis, siteId, postId);
  }

}