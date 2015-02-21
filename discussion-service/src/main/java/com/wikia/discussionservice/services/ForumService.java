package com.wikia.discussionservice.services;

import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.Forums;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Forum Service - Responsible for handling all actions around
 * the forum domain.
 */
@Service
public class ForumService {

  public Forums getForums(Integer start, Integer count) {

    List<Forum> forumList = new ArrayList<>();

    int total = 20;
    IntStream.range(1, count+1).forEach(
        i -> {
          Forum forum = new Forum();
          forum.setId(i);
          forum.setName(String.format("Forum: %s", i));
          forumList.add(forum);
        }
    );

    Forums forums = new Forums();
    forums.setForums(forumList);
    forums.setTotal(total);
    forums.setStart(start);
    return forums;
  }
}
