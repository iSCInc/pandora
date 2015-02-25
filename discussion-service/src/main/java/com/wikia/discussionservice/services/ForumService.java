package com.wikia.discussionservice.services;

import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.Forums;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Forum Service - Responsible for handling all actions around
 * the forum domain.
 */
public class ForumService {

  public Optional<Forums> getForums(int offset, int limit) {

    List<Forum> forumList = new ArrayList<>();

    int total = 20;
    int start = offset == 1
        ? 1
        : (limit * offset) - (limit - 1);

    IntStream.range(start, Math.min(start + limit, total)).forEach(
        i -> {
          Forum forum = new Forum(i, String.format("Forum: %s", i), new ArrayList<>());
          forumList.add(forum);
        }
    );

    Forums forums = new Forums();
    forums.setForums(forumList);
    forums.setTotal(total);
    forums.setLimit(limit);
    forums.setOffset(offset);
    
    return Optional.of(forums);
  }
}
