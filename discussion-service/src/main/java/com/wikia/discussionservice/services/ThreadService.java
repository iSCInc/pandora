package com.wikia.discussionservice.services;

import com.wikia.discussionservice.domain.ForumThread;
import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.domain.User;
import lombok.NonNull;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class ThreadService {

  @NonNull
  private final PostService postService;

  @Inject
  public ThreadService(PostService postService) {
    this.postService = postService;
  }

  public List<ForumThread> createThreadList(int offset, int limit) {
      List<ForumThread> threads = new ArrayList<>();

      int start = offset == 1
          ? 1
          : (limit * offset) - (limit - 1);

      IntStream.range(start, limit + 1).forEach(
          i -> {
            ForumThread thread = new ForumThread();

            User user = new User();
            user.setName(String.format("User%s", i));
            user.setJoinDate(LocalDateTime.now());
            user.setId(i);
            
            List<Post> posts = postService.createPosts();
            
            thread.setLastPost(posts.get(posts.size() - 1));
            thread.setPosts(posts);
            
            thread.setId(i);
            thread.setThreadStarter(user);
            threads.add(thread);
          }
      );

      return threads;
    }

  public Optional<ForumThread> getThread(int siteId, int threadId, int offset, int limit) {
    ForumThread thread = new ForumThread();
    thread.setId(threadId);

    User user = new User();
    user.setId(new Random().nextInt(100000));
    user.setName(String.format("User%s", user.getId()));
    user.setJoinDate(LocalDateTime.now());

    List<Post> posts = postService.createPosts(offset, limit);

    thread.setLastPost(posts.get(posts.size() - 1));
    thread.setPosts(posts);

    thread.setId(new Random().nextInt(100000));
    thread.setThreadStarter(user);
    
    return Optional.of(thread);
  }

}