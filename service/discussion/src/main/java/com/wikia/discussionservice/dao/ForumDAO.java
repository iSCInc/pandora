package com.wikia.discussionservice.dao;

import com.google.common.base.Preconditions;
import com.wikia.discussionservice.domain.Forum;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.wikia.discussionservice.services.JedisService;
import lombok.NonNull;

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
    createRoot();
  }

  private static final int ROOT_ID = 1;
  private static final int DEFAULT_SITE_ID = 1;
  private static int SEQUENCE = 2;

  private void createRoot() {
    if (!retrieveForum(DEFAULT_SITE_ID, ROOT_ID).isPresent()) {
      contentDAO.createContent(DEFAULT_SITE_ID,
                               new Forum(ROOT_ID, -1, "Root", new ArrayList<>(), new ArrayList<>()));
    }
  }

  public ArrayList<Forum> retrieveForums(int siteId) {
    return contentDAO.getItems(siteId, Forum.class);
  }

  public Optional<Forum> retrieveForum(int siteId, int forumId) {
    return Optional.ofNullable(contentDAO.getContent(siteId, forumId, Forum.class));
  }

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
      forum = contentDAO.createContent(siteId, forum);
      updateForum(siteId, parentForum.get());
    }

    return Optional.ofNullable(forum);
  }

  public Optional<Forum> deleteForum(int siteId, int forumId) {
    Preconditions.checkArgument(forumId != ROOT_ID, "Cannot delete the root forum.");
    Optional<Forum> deletedForum = retrieveForum(siteId, forumId);
    if (deletedForum.isPresent()) {
      contentDAO.deleteContent(siteId, forumId, Forum.class);
      // delete the forum from the Root's children then
      // delete forum from children's children (not recursive)
      Forum rootForum = retrieveForum(siteId, ROOT_ID).get();
      rootForum.getChildren().remove(deletedForum.get());
      for(Forum forum: rootForum.getChildren()) {
        forum.getChildren().remove(deletedForum.get());
        updateForum(siteId, forum);
      }
      updateForum(siteId, rootForum);
    }

    return deletedForum;
  }

  public Optional<Forum> updateForum(int siteId, Forum forum) {
    Optional<Forum> retrievedForum = retrieveForum(siteId, forum.getId());

    if (retrievedForum.isPresent()) {
      // Changes may be anything, including list of sub forums
      // Hence removing the old forum and re-writing it
      contentDAO.deleteContent(siteId, forum.getId(), Forum.class);
      return Optional.ofNullable(contentDAO.createContent(siteId, forum));
    }

    return Optional.empty();
  }
}
