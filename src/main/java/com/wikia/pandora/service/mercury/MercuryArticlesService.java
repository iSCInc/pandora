package com.wikia.pandora.service.mercury;

import com.google.common.base.Optional;

import com.sun.jersey.api.NotFoundException;
import com.wikia.pandora.core.Article;
import com.wikia.pandora.core.Comment;
import com.wikia.pandora.service.ArticleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MercuryArticlesService extends MercuryService implements ArticleService {

  public MercuryArticlesService(MercuryGateway mercuryGateway) {
    super(mercuryGateway);

  }

  public Article getArticleByTitle(String wikia, String title) {
    Map<String, Object> responseMap = null;
    try {
      responseMap = this.mercuryGateway.getArticle(wikia, title);

      Map<String, Object> dataMap = ((Map<String, Object>) responseMap.get("data"));
      Map<String, Object> detailsMap = (Map<String, Object>) dataMap.get("details");
      Map<String, Object> articleMap = (Map<String, Object>) dataMap.get("article");

      Article article = new Article();
      article.setId((Integer) detailsMap.get("id"));
      article.setTitle((String) detailsMap.get("title"));
      article.setContent((String) articleMap.get("content"));
      return article;
    } catch (IOException e) {
      throw new NotFoundException("No such article");
    }
  }

  @Override
  public List<Comment> getArticleComments(String wikia, String title) {
    Map<String, Object> responseMap = null;
    try {
      responseMap = this.mercuryGateway.getCommentsForArticle(wikia, title);
      Map<String, Object> payload = (Map<String, Object>) responseMap.get("payload");
      List<Map<String, Object>> comments = (List<Map<String, Object>>) payload.get("comments");
      List<Comment> commentList = new ArrayList<Comment>();
      for (Map<String, Object> mercuryComment : comments) {
        Comment comment = new Comment();
        comment.setId(Long.valueOf(((Integer) mercuryComment.get("id"))));
        comment.setText((String) mercuryComment.get("text"));
        comment.setCreated(Long.valueOf((Integer) mercuryComment.get("created")));
        comment.setUserName((String) mercuryComment.get("userName"));
        comment.setWikiaName(wikia);
        comment.setArticleName(title);
        commentList.add(comment);
      }
      return commentList;
    } catch (IOException e) {
      throw new NotFoundException("No such article");
    }
  }

  @Override
  public Comment getComment(String wikia, String title, Long commentId) {
    Comment comment = new Comment();
    comment.setWikiaName(wikia);
    comment.setArticleName(title);
    comment.setText("comment test");
    comment.setId(commentId);
    return comment;
  }
}
