package com.wikia.pandora.resources.halbuilder;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;

public class HalResourceBase {

  private final RepresentationFactory representationFactory;

  public HalResourceBase(RepresentationFactory representationFactory) {
    this.representationFactory = representationFactory;
  }

  public RepresentationFactory getRepresentationFactory() {
    return representationFactory;
  }

}
