package com.wikia.userpreference.dao;

import com.wikia.userpreference.domain.UserPreference;

import com.google.common.collect.ImmutableList;
import lombok.Data;

import java.util.Optional;


@Data
public class UserPreferenceDAO {

  private WikiCitiesDAO wikiCitiesDAO;

  public UserPreferenceDAO(WikiCitiesDAO wikiCitiesDAO) {
    this.wikiCitiesDAO = wikiCitiesDAO;
  }

  public Optional<ImmutableList<UserPreference>> lookup(Integer userId) {
    return this.wikiCitiesDAO.getUserPreferences(userId);
  }

}
