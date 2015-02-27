package com.wikia.discussionservice.dao;

import com.wikia.discussionservice.domain.Forum;

import java.util.*;
import java.util.stream.IntStream;


public class ForumDAO {
  
  private static final Map<Integer, Forum> FORUMS = new HashMap<>();

  static {
    FORUMS.put(1, new Forum(1, "Root", new ArrayList<>(), new ArrayList<>()));
  }

  private static int SEQUENCE = 2;

  public List<Forum> retrieveForums(int siteId) {
    return ForumDAO.FORUMS.get(1).getChildren();
  }
  
  public Optional<Forum> retrieveForum(int siteId, int forumId) {
    Forum retrievedForum = null;
    retrievedForum = ForumDAO.FORUMS.get(forumId);

    return Optional.ofNullable(retrievedForum);
  }


  public List<Forum> createForumList(int offset, int limit) {
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

  public Optional<Forum> createForum(int siteId, int parentId, String forumName) {
    Optional<Forum> parentForum = retrieveForum(siteId, parentId);
    
    Forum newForum = null;
    if (parentForum.isPresent()) {
      int forumId = ForumDAO.SEQUENCE++;
      newForum = new Forum(forumId, forumName,
          new ArrayList<>(), new ArrayList<>());
      parentForum.get().getChildren().add(newForum);
      ForumDAO.FORUMS.put(forumId, newForum);
    }
    
    return Optional.ofNullable(newForum);
  }
}
