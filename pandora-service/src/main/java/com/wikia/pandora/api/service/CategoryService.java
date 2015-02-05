package com.wikia.pandora.api.service;


import com.wikia.pandora.domain.Article;
import com.wikia.pandora.domain.Category;

import java.util.List;

public interface CategoryService {

  Category getCategory(String wikia, String category);

  List<Article> getCategoryArticles(String wikia, String categoryName, int limit, int offset);
}
