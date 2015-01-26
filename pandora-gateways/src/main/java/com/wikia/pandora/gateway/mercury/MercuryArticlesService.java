package com.wikia.pandora.gateway.mercury;

import com.sun.jersey.api.NotFoundException;
import com.wikia.pandora.api.service.ArticleService;
import com.wikia.pandora.core.domains.Article;
import com.wikia.pandora.core.domains.ArticleWithContent;
import com.wikia.pandora.core.domains.ArticleWithDescription;
import com.wikia.pandora.core.domains.Comment;
import com.wikia.pandora.core.domains.builder.PojoBuilderFactory;

import org.apache.commons.lang.NotImplementedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MercuryArticlesService extends MercuryService implements ArticleService {

  public MercuryArticlesService(MercuryGateway mercuryGateway) {
    super(mercuryGateway);

  }

  public Article getArticleByTitle(String wikia, String title) {
    Map<String, Object> responseMap;
    try {
      responseMap = this.mercuryGateway.getArticle(wikia, title);

      Map<String, Object> dataMap = ((Map<String, Object>) responseMap.get("data"));
      Map<String, Object> detailsMap = (Map<String, Object>) dataMap.get("details");
      Map<String, Object> articleMap = (Map<String, Object>) dataMap.get("article");

      return PojoBuilderFactory.getArticleBuilder()
          .withId((Integer) detailsMap.get("id"))
          .withTitle((String) detailsMap.get("title"))
          .build();
    } catch (IOException e) {
      throw new NotFoundException("No such article");
    }
  }

  @Override
  public ArticleWithDescription getArticleWithDescriptionByTitle(String wikia, String title) {
    throw new NotImplementedException();
  }

  @Override
  public ArticleWithContent getArticleWithContentByTitle(String wikia, String title) {
    throw new NotImplementedException();
  }

  @Override
  public List<Comment> getArticleComments(String wikia, String title) {
    Map<String, Object> responseMap;
    try {
      responseMap = this.mercuryGateway.getCommentsForArticle(wikia, title);
      Map<String, Object> payload = (Map<String, Object>) responseMap.get("payload");
      List<Map<String, Object>> comments = (List<Map<String, Object>>) payload.get("comments");
      List<Comment> commentList = new ArrayList<Comment>();
      for (Map<String, Object> mercuryComment : comments) {

        Comment comment = PojoBuilderFactory.getCommentBuilder()
            .withId(Long.valueOf(((Integer) mercuryComment.get("id"))))
            .withText((String) mercuryComment.get("text"))
            .withCreated(Long.valueOf((Integer) mercuryComment.get("created")))
            .withUserName((String) mercuryComment.get("userName"))
            .withWikiaName(wikia)
            .withArticleName(title)
            .build();
        commentList.add(comment);
      }
      return commentList;
    } catch (IOException e) {
      throw new NotFoundException("No such article");
    }
  }

  @Override
  public Comment getComment(String wikia, String title, Long commentId) {
    Comment comment = PojoBuilderFactory.getCommentBuilder()
        .withWikiaName(wikia)
        .withArticleName(title)
        .withText("TODO LATER")//TODO
        .withId(commentId)
        .build();

    return comment;
  }
}
