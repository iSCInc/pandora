package com.wikia.pandora.api.service;

import com.wikia.pandora.domain.Revision;

public interface RevisionService {

  Revision getRevisionById(String wikia, Long revId, boolean withContent);

}
