package com.wikia.userpreference.dao;

import com.wikia.userpreference.domain.UserPreference;

import com.google.common.collect.ImmutableList;

import java.util.Optional;

public interface WikiCitiesDAO {

  public Optional<ImmutableList<UserPreference>> getUserPreferences(Integer userId);


}
