package com.wikia.discussionservice.mappers;

/**
 * Created by armon on 2/27/15.
 */
// TODO: Fix this!
public interface DataStore {
  public Object get(int siteId, int objectId);
  public void set(int siteId, int objectId, String value);
}
