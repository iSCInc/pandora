package com.wikia.pandora.core.domains.builder;


public class PojoBuilderFactory {

  public static ArticleBuilder getArticleBuilder() {
    return ArticleBuilder.anArticle();
  }

  public static ArticleWithContentBuilder getArticleWithContentBuilder() {
    return ArticleWithContentBuilder.anArticleWithContent();
  }

  public static CommentBuilder getCommentBuilder() {
    return CommentBuilder.aComment();
  }

  public static UserBuilder getUserBuilder() {
    return UserBuilder.anUser();

  }
}
