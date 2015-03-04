package com.wikia.discussionservice.services;

import com.wikia.discussionservice.domain.Post;

import java.time.LocalDateTime;

import java.time.Period;
import java.util.*;
import java.util.stream.IntStream;

public class PostService {

  private static Map<Integer, Post> POSTS = new HashMap<>();
  private static int SEQUENCE = 1;

  public Optional<Post> getPost(int siteId, int postId) {
    Post post = POSTS.get(postId);
    return Optional.ofNullable(post);
  }

  public Optional<Post> createPost(int siteId, int threadId, Post post) {
    int postId = SEQUENCE++;
    
    post.setThreadId(threadId);
    post.setId(postId);
    POSTS.put(postId, post);
    return Optional.of(post);
  }
}
