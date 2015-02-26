package com.wikia.discussionservice.services;

import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.domain.ForumThread;
import lombok.NonNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Forum Service
 * Responsible for handling all actions around
 * the forum domain.
 */
public class ForumService {

  @NonNull
  private final ThreadService threadService;

  @Inject
  public ForumService(ThreadService threadService) {
    this.threadService = threadService;
  }

  public Optional<ForumRoot> getForums(int siteId, int offset, int limit) {

    int total = 20;
    List<Forum> forumList = createForumList(offset, limit);

    ForumRoot forumRoot = new ForumRoot();
    forumRoot.setForums(forumList);
    forumRoot.setTotal(total);
    forumRoot.setLimit(limit);
    forumRoot.setOffset(offset);
    forumRoot.setSiteId(siteId);

    return Optional.of(forumRoot);
  }

  private List<Forum> createForumList(int offset, int limit) {
    List<Forum> forumList = new ArrayList<>();

    int start = offset == 1
        ? 1
        : (limit * offset) - (limit - 1);

    IntStream.range(start, limit + 1).forEach(
        i -> {
          Forum forum =
              new Forum(i, String.format("Forum: %s", i), new ArrayList<>(), new ArrayList<>());
          forumList.add(forum);
        }
    );

    return forumList;
  }


  public Forum getForum(int siteId, int id, int offset, int limit) {
    if (id % 2 == 0) {
      List<Forum> forumList = createForumList(1, 10);
      return new Forum(id, "test forum", forumList, new ArrayList<>());
    } else {
      List<ForumThread> threads = threadService.createThreadList(offset, limit);
      return new Forum(id, "test forum", new ArrayList<>(), threads);
    }
  }
}
