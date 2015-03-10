package com.wikia.communitydata.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommunityDataResourceTest {

  @Test
  public void testGetHelloWorld() {
    RepresentationFactory representationFactory = new StandardRepresentationFactory();
    CommunityDataResource resource = new CommunityDataResource(representationFactory);
    Representation representation = resource.getHelloWorld("John");
    assertEquals("Hello, John", representation.getProperties().get("Greeting"));
  }
}
