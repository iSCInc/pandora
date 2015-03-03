package com.wikia.discussionservice.dao;

import com.google.common.base.Preconditions;
import com.wikia.discussionservice.domain.Forum;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.stream.IntStream;

/**
 * THIS IS TEMPORARY THROWAWAY CODE
 */
public class ForumDAO {
  
  private static final Map<Integer, Forum> FORUMS = new HashMap<>();

  static {
    FORUMS.put(1, new Forum(1, -1, "Root", new ArrayList<>(), new ArrayList<>()));
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
              new Forum(i, 1, String.format("Forum: %s", i), new ArrayList<>(), new ArrayList<>());
          forumList.add(forum);
        }
    );

    return forumList;
  }

  @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  public Optional<Forum> createForum(int siteId, Forum forum) {
    Optional<Forum> parentForum = retrieveForum(siteId, forum.getParentId());
    
    if (parentForum.isPresent()) {
      
      boolean foundExisting = false;
      for(Forum childForum: parentForum.get().getChildren()) {
        if (childForum.getName().equalsIgnoreCase(forum.getName())) {
          foundExisting = true;
          break;
        }
      }
      if (!foundExisting) {
        int forumId = ForumDAO.SEQUENCE++;
        forum.setId(forumId);
        parentForum.get().getChildren().add(forum);
        ForumDAO.FORUMS.put(forumId, forum);
      } else {
        forum = null;
      }
    }
    
    return Optional.ofNullable(forum);
  }

  public Optional<Forum> deleteForum(int siteId, int forumId) {
    Preconditions.checkArgument(forumId != 1, "Cannot delete the root forum.");
    Forum deletedForum = null;
    if (ForumDAO.FORUMS.containsKey(forumId)) {
      deletedForum = ForumDAO.FORUMS.remove(forumId);
      // delete the forum from the Root's children then 
      // delete forum from children's children (not recursive)
      Forum rootForum = retrieveForum(siteId, 1).get();
      rootForum.getChildren().remove(deletedForum);
      for(Forum forum: rootForum.getChildren()) {
        forum.getChildren().remove(deletedForum);
      }
    }
    
    return Optional.ofNullable(deletedForum);
  }

  public Optional<Forum> updateForum(int siteId, Forum forum) {
    Optional<Forum> retrievedForum = retrieveForum(siteId, forum.getId());
    
    if (retrievedForum.isPresent()) {
      retrievedForum.get().setName(forum.getName());
    }

    return Optional.empty();
  }
}
