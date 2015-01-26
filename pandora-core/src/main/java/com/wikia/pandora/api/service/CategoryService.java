package com.wikia.pandora.api.service;


import com.wikia.pandora.core.domains.Category;

public interface CategoryService {

  Category getCategory(String wikia, String category);
}
