package com.wikia.pandora.resources;

import com.wikia.pandora.core.domain.mock.AdsContextMock;
import com.wikia.pandora.core.domain.mock.TopContributorMock;

import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import java.util.ArrayList;
import java.util.List;

public class HalMercuryMockResource {


  private static final RepresentationFactory
      innerRepresentationFactory =
      new DefaultRepresentationFactory();


  public static Representation getTopContributors(String wikia, String title) {
    Representation rep = innerRepresentationFactory.newRepresentation("/topContributorsMock");
    List<TopContributorMock> mockList = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      mockList.add(new TopContributorMock());
    }
    rep.withProperty("users", mockList);
    return rep;
  }

  public static Representation getAdsContext(String wikia, String title) {
    Representation rep = innerRepresentationFactory.newRepresentation("/AdsContextMock");
    rep.withProperty("ads", new AdsContextMock());
    return rep;
  }


}
