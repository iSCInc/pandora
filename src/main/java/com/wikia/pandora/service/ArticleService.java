package com.wikia.pandora.service;

import com.google.common.base.Optional;
import com.wikia.pandora.api.Article;
import com.wikia.pandora.gateway.MercuryGateway;

import java.io.IOException;
import java.util.Map;

public class ArticleService {

    private final MercuryGateway mercuryGateway;

    public ArticleService(MercuryGateway mercuryGateway) {
        this.mercuryGateway = mercuryGateway;
    }

    public Optional<Article> lookup(String wikia, String title) throws IOException {
        Map<String, Object> responseMap = this.mercuryGateway.getArticle(wikia, title);
        Map<String, Object> data = (Map<String, Object>)responseMap.get("data");
        Map<String, Object> details = (Map<String, Object>)data.get("details");
        Map<String, Object> article = (Map<String, Object>)data.get("article");
        return Optional.of(new Article.Builder()
                .id((Integer)details.get("id"))
                .title((String)details.get("title"))
                .content((String)article.get("content"))
                .build());
    }
}
