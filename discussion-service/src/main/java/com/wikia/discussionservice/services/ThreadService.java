package com.wikia.discussionservice.services;

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

  @Inject
  public ThreadService(PostService postService) {
    super();
    this.postService = postService;
  }

  public static String getType() {
    return "thread";
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
}