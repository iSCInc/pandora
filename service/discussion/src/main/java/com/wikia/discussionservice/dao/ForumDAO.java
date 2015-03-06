package com.wikia.discussionservice.dao;

import com.google.common.base.Preconditions;
import com.wikia.discussionservice.domain.Forum;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

/**
 * THIS IS TEMPORARY THROWAWAY CODE
 */
public class ForumDAO extends ContentDAO {

  public ForumDAO() {
    super();
    createContent(1, new Forum(ROOT_ID, -1, "Root", new ArrayList<>(), new ArrayList<>()));
  }
//  private static final Map<Integer, Forum> FORUMS = new HashMap<>();
//
//  static {
//    FORUMS.put(1, new Forum(1, -1, "Root", new ArrayList<>(), new ArrayList<>()));
//  }

  private static final int ROOT_ID = 1;
  private static int SEQUENCE = 2;

  public ArrayList<Forum> retrieveForums(int siteId) {
    return getItems(siteId, Forum.class);
//    return ForumDAO.FORUMS.get(1).getChildren();
  }

  public Forum retrieveForum(int siteId, int forumId) {
    return getContent(siteId, forumId, Forum.class);
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
    Forum parentForum = retrieveForum(siteId, forum.getParentId());
    boolean foundExisting = false;

    if (parentForum == null) {
      return null;
    }
    for (Forum childForum : parentForum.getChildren()) {
      if (childForum.getName().equalsIgnoreCase(forum.getName())) {
        foundExisting = true;
        break;
      }
    }

    if (!foundExisting) {
      int forumId = ForumDAO.SEQUENCE++;
      forum.setId(forumId);
      parentForum.getChildren().add(forum);
      forum = createContent(siteId, forum);
      createContent(siteId, parentForum);
    }

    return Optional.ofNullable(forum);
  }

  public Optional<Forum> deleteForum(int siteId, int forumId) {
    Preconditions.checkArgument(forumId != ROOT_ID, "Cannot delete the root forum.");
    Forum deletedForum = retrieveForum(siteId, forumId);
    if (deletedForum != null) {
      deleteContent(siteId, forumId, Forum.class);
      // delete the forum from the Root's children then
      // delete forum from children's children (not recursive)
      Forum rootForum = retrieveForum(siteId, ROOT_ID);
      rootForum.getChildren().remove(deletedForum);
      for(Forum forum: rootForum.getChildren()) {
        forum.getChildren().remove(deletedForum);
      }
      createForum(siteId, rootForum);
    }

    return Optional.ofNullable(deletedForum);
  }

  public Optional<Forum> updateForum(int siteId, Forum forum) {
    Forum retrievedForum = retrieveForum(siteId, forum.getId());
    
    if (retrievedForum != null) {
      retrievedForum.setName(forum.getName());
      createContent(siteId, forum);
    }

    return Optional.empty();
  }
}
