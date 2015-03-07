package com.wikia.discussionservice.dao;

import com.google.common.base.Preconditions;
import com.wikia.discussionservice.domain.Forum;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * THIS IS TEMPORARY THROWAWAY CODE
 */
public class ForumDAO extends ContentDAO {

  public ForumDAO() {
    super();
    createRoot();
  }

  private static final int ROOT_ID = 1;
  private static final int DEFAULT_SITE_ID = 1;
  private static int SEQUENCE = 2;

  private void createRoot() {
    if (!retrieveForum(DEFAULT_SITE_ID, ROOT_ID).isPresent()) {
      createContent(DEFAULT_SITE_ID, new Forum(ROOT_ID, -1, "Root", new ArrayList<>(), new ArrayList<>()));
    }
  }

  public ArrayList<Forum> retrieveForums(int siteId) {
    return getItems(siteId, Forum.class);
  }

  public Optional<Forum> retrieveForum(int siteId, int forumId) {
    return Optional.ofNullable(getContent(siteId, forumId, Forum.class));
  }


  public List<Forum> createForumList(int offset, int limit) {
    List<Forum> forumList = new ArrayList<>();

    int start = offset == 1
        ? 1
        : (limit * offset) - (limit - 1);

    IntStream.range(start, limit + 1).forEach(
        i -> {
          Forum forum =
              new Forum(i, ROOT_ID, String.format("Forum: %s", i), new ArrayList<>(), new ArrayList<>());
          forumList.add(forum);
        }
    );

    return forumList;
  }

  @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
  public Optional<Forum> createForum(int siteId, Forum forum) {
    Optional<Forum> parentForum = retrieveForum(siteId, forum.getParentId());
    boolean foundExisting = false;

    if (!parentForum.isPresent()) {
      return Optional.empty();
    }

    for (Forum childForum : parentForum.get().getChildren()) {
      if (childForum.getName().equalsIgnoreCase(forum.getName())) {
        foundExisting = true;
        break;
      }
    }

    if (!foundExisting) {
      int forumId = ForumDAO.SEQUENCE++;
      forum.setId(forumId);
      parentForum.get().getChildren().add(forum);
      forum = createContent(siteId, forum);
      updateForum(siteId, parentForum.get());
    }

    return Optional.ofNullable(forum);
  }

  public Optional<Forum> deleteForum(int siteId, int forumId) {
    Preconditions.checkArgument(forumId != ROOT_ID, "Cannot delete the root forum.");
    Optional<Forum> deletedForum = retrieveForum(siteId, forumId);
    if (deletedForum.isPresent()) {
      deleteContent(siteId, forumId, Forum.class);
      // delete the forum from the Root's children then
      // delete forum from children's children (not recursive)
      Forum rootForum = retrieveForum(siteId, ROOT_ID).get();
      rootForum.getChildren().remove(deletedForum.get());
      for(Forum forum: rootForum.getChildren()) {
        forum.getChildren().remove(deletedForum.get());
      }
      createForum(siteId, rootForum);
    }

    return deletedForum;
  }

  public Optional<Forum> updateForum(int siteId, Forum forum) {
    Optional<Forum> retrievedForum = retrieveForum(siteId, forum.getId());

    if (retrievedForum.isPresent()) {
      // Changes may be anything, including list of sub forums
      // Hence removing the old forum and re-writing it
      deleteContent(siteId, forum.getId(), Forum.class);
      return Optional.ofNullable(createContent(siteId, forum));
    }

    return Optional.empty();
  }
}
