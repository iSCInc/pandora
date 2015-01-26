package com.wikia.pandora.gateway.mediawiki;

public class MediawikiService {

  private MediawikiGateway gateway;

  public MediawikiService(MediawikiGateway gateway) {
    this.gateway = gateway;
  }

  public MediawikiGateway getGateway() {
    return gateway;
  }
}
