package com.wikia.discussionservice.dao;

import com.google.common.base.Preconditions;
import com.wikia.discussionservice.domain.Forum;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import lombok.NonNull;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;

/**
 * THIS IS TEMPORARY THROWAWAY CODE
 */
public class ForumDAO {

  @NonNull
  ContentDAO contentDAO;

  @Inject
  public ForumDAO(ContentDAO contentDAO) {
    this.contentDAO = contentDAO;
//    createRoot();
  }

  public static final int ROOT_ID = 1;
  public static final int PARENT_ROOT_ID = -1;
  public static final int DEFAULT_SITE_ID = 1;
  private static int SEQUENCE = 2;

//  private void createRoot() {
//    if (!retrieveForum(DEFAULT_SITE_ID, ROOT_ID).isPresent()) {
//      contentDAO.createContent(jedis, DEFAULT_SITE_ID,
//                               new Forum(ROOT_ID, -1, "Root", new ArrayList<>(), new ArrayList<>()));
//    }
//  }

  public ContentDAO getContentDAO() {
    return contentDAO;
  }

  public ArrayList<Forum> retrieveForums(Jedis jedis, int siteId) {
    return contentDAO.getItems(jedis, siteId, Forum.class);
  }

  public Optional<Forum> retrieveForum(Jedis jedis, int siteId, int forumId) {
    return Optional.ofNullable(contentDAO.getContent(jedis, siteId, forumId, Forum.class));
  }

  public Optional<Forum> createForum(Jedis jedis, int siteId, Forum forum) {
    Optional<Forum> parentForum = retrieveForum(jedis, siteId, forum.getParentId());
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
      forum = contentDAO.createContent(jedis, siteId, forum);
      updateForum(jedis, siteId, parentForum.get());
    }

    return Optional.ofNullable(forum);
  }

  public Optional<Forum> deleteForum(Jedis jedis, int siteId, int forumId) {
    Preconditions.checkArgument(forumId != ROOT_ID, "Cannot delete the root forum.");
    Optional<Forum> deletedForum = retrieveForum(jedis, siteId, forumId);
    if (deletedForum.isPresent()) {
      contentDAO.deleteContent(jedis, siteId, forumId, Forum.class);
      // delete the forum from the Root's children then
      // delete forum from children's children (not recursive)
      Forum rootForum = retrieveForum(jedis, siteId, ROOT_ID).get();
      rootForum.getChildren().remove(deletedForum.get());
      for(Forum forum: rootForum.getChildren()) {
        forum.getChildren().remove(deletedForum.get());
        updateForum(jedis, siteId, forum);
      }
      updateForum(jedis, siteId, rootForum);
    }

    return deletedForum;
  }

  public Optional<Forum> updateForum(Jedis jedis, int siteId, Forum forum) {
    Optional<Forum> retrievedForum = retrieveForum(jedis, siteId, forum.getId());

    if (retrievedForum.isPresent()) {
      // Changes may be anything, including list of sub forums
      // Hence removing the old forum and re-writing it
      contentDAO.deleteContent(jedis, siteId, forum.getId(), Forum.class);
      return Optional.ofNullable(contentDAO.createContent(jedis, siteId, forum));
    }

    return Optional.empty();
  }
}
