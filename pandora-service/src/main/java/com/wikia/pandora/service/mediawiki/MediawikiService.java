package com.wikia.pandora.service.mediawiki;

import com.wikia.pandora.gateway.mediawiki.MediawikiGateway;

public class MediawikiService {

  private MediawikiGateway gateway;

  public MediawikiService(MediawikiGateway gateway) {
    this.gateway = gateway;
  }

  public MediawikiGateway getGateway() {
    return gateway;
  }
}
