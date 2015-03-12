package com.wikia.discussionservice.services;

import com.wikia.discussionservice.dao.PostDAO;
import com.wikia.discussionservice.domain.Post;
import lombok.NonNull;

import javax.inject.Inject;

import java.time.LocalDateTime;

import java.time.Period;
import java.util.*;
import java.util.Optional;
import java.util.stream.IntStream;

public class PostService {

  @NonNull
  private final PostDAO postDAO;

  @Inject
  public PostService(PostDAO postDAO) {
    this.postDAO = postDAO;
  }

//  public Optional<Post> getPost(int siteId, int postId) {
//    Post post = POSTS.get(postId);
//    return Optional.ofNullable(post);
//  }
//
//  public Optional<Post> createPost(int siteId, int threadId, Post post) {
//    int postId = SEQUENCE++;
//
//    post.setThreadId(threadId);
//    post.setId(postId);
//    POSTS.put(postId, post);
//    return Optional.of(post);
//  }

  public Optional<Post> getPost(int siteId, int postId) {
    return postDAO.getPost(siteId, postId);
  }

  public Optional<Post> createPost(int siteId, int threadId, Post post) {
    return postDAO.createPost(siteId, threadId, post);
  }

  public Optional<Post> deletePost(int siteId, int postId) {
    return postDAO.deletePost(siteId, postId);
  }

}