package com.wikia.pandora.core.domain.builder;


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

  public static CategoryBuilder getCategoryBuilder() {
    return CategoryBuilder.aCategory();
  }


  public static MediaBuilder getMediaBuilder() {
    return MediaBuilder.aMedia();
  }

  public static RevisionBuilder getRevisionBuilder() {
    return RevisionBuilder.aRevision();
  }
}
