package com.wikia.pandora.service.mercury;

import com.google.common.base.Optional;

import com.sun.jersey.api.NotFoundException;
import com.wikia.pandora.core.Comment;
import com.wikia.pandora.service.CommentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class MercuryCommentService extends MercuryService implements CommentService {

  private static final Logger logger = LoggerFactory.getLogger(MercuryCommentService.class);

  public MercuryCommentService(MercuryGateway gateway) {
    super(gateway);
  }


  @Override
  public Optional<Comment> getCommentsForArticle(String wikia, String title) {
    Map<String, Object> responseMap = null;
    try {
      responseMap = this.mercuryGateway.getCommentsForArticle(wikia, title);
      logger.debug(String.valueOf(responseMap));
//      Map<String, Object> data = ((Map<String, Object>) responseMap.getArticle("data"));
//      Map<String, Object> details = (Map<String, Object>) data.getArticle("details");
//      Map<String, Object> article = (Map<String, Object>) data.getArticle("article");
//      return Optional.of(new Article.Builder()
//                             .id((Integer) details.getArticle("id"))
//                             .title((String) details.getArticle("title"))
//                             .content((String) article.getArticle("content"))
//                             .build());
    } catch (IOException e) {
      throw new NotFoundException("No such article");
    }
    return Optional.of(new Comment());
  }
}

