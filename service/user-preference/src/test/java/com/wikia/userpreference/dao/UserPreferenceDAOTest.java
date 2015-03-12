package com.wikia.userpreference.dao;

import static org.junit.Assert.*;

import com.wikia.userpreference.domain.UserPreference;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UserPreferenceDAOTest {

  @Test
  public void testLookup() throws Exception {
    Integer userId = 1234;
    String name = "a-key";
    String value = "a-value";
    ImmutableList<UserPreference> userPreferenceList = new ImmutableList.Builder<UserPreference>()
        .add(UserPreference.newBuilder().name(name).value(value).build()).build();
    WikiCitiesDAO wikiCitiesDAO = Mockito.mock(WikiCitiesDAO.class);
    Mockito.when(wikiCitiesDAO.getUserPreferences(userId)).thenReturn(Optional.of(userPreferenceList));

    UserPreferenceDAO userPreferenceDAO = new UserPreferenceDAO(wikiCitiesDAO);

    Optional<ImmutableList<UserPreference>> result = userPreferenceDAO.lookup(userId);
    assertTrue(result.isPresent());
    assertEquals(name, result.get().get(0).getName());
    assertEquals(value, result.get().get(0).getValue());
  }

}