package com.wikia.discussionservice.services;

import com.wikia.discussionservice.domain.Post;
import com.wikia.discussionservice.domain.User;

import java.time.Instant;
import java.time.LocalDateTime;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class PostService extends ContentService {

  public static String getType() {
    return "post";
  }

  public PostService() {
    super();
  }

  public List<Post> createPosts() {
    return createPosts(0, new Random().nextInt(100));
  }

  public List<Post> createPosts(int offset, int limit) {
    List<Post> posts = new ArrayList<>();

    IntStream.range(offset, limit).forEach(
        i -> {
          Post post = new Post();
          post.setId(i);
          post.setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed " +
              "do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim" +
              " ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
              "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit " +
              "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
              "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
              "officia deserunt mollit anim id est laborum");
          post.setDate(LocalDateTime.now().minus(Period.ofMonths(new Random().nextInt(24))));

          User user = new User();
          user.setId(new Random().nextInt(1000000));
          user.setName(Instant.now().toString());

          post.setPoster(user);

          posts.add(post);
        }
    );

    return posts;
  }

  public Optional<Post> getPost(int siteId, int postId) {
    return Optional.empty();
  }
}
