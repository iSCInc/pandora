package com.wikia.pandora.service.mediawiki;

import com.wikia.mwapi.domain.ApiResponse;
import com.wikia.mwapi.domain.Page;
import com.wikia.pandora.api.service.RevisionService;
import com.wikia.pandora.domain.Revision;
import com.wikia.pandora.domain.builder.RevisionBuilder;
import com.wikia.pandora.gateway.mediawiki.MediawikiGateway;

public class MediawikiRevisionService extends MediawikiService implements RevisionService {

  public MediawikiRevisionService(MediawikiGateway gateway) {
    super(gateway);
  }


  public Revision getRevisionById(String wikia, Long revId, boolean withContent) {
    ApiResponse apiResponse =
        withContent ? this.getGateway().getRevisionByIdWithContent(wikia, revId)
                    : this.getGateway().getRevisionById(wikia, revId);

    Page page = apiResponse.getQuery().getFirstPage();
    if (page != null) {
      com.wikia.mwapi.domain.Revision rev = page.getFirstRevision();
      Revision revision = RevisionBuilder.aRevision()
          .withUser(rev.getUser())
          .withRevId(rev.getRevId())
          .withParentId(rev.getParentId())
          .withComment(rev.getComment())
          .withLastRevId(page.getLastrevid())
          .withTimestamp(rev.getTimestamp())
          .withTitle(page.getTitle())
          .withContent(rev.getContent())
          .withPageId(page.getPageId())
          .build();
      return revision;
    }
    return null;
  }
}