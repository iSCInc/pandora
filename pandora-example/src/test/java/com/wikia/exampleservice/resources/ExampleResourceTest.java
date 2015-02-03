package com.wikia.exampleservice.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleResourceTest {

  @Test
  public void testGetHelloWorld() throws Exception {
    RepresentationFactory representationFactory = new StandardRepresentationFactory();
    ExampleResource resource = new ExampleResource(representationFactory);
    Representation representation = resource.getHelloWorld("John");
    assertEquals(representation.getProperties().get("Greatings"), "John");
  }

  @Test
  public void testGetSimplePojo() throws Exception {
    RepresentationFactory representationFactory = new StandardRepresentationFactory();
    ExampleResource resource = new ExampleResource(representationFactory);
    Representation representation = resource.getSimplePojo(7, "John", true);
    assertEquals(representation.getProperties().get("id"), 7);
    assertEquals(representation.getProperties().get("someString"), "John");
    assertEquals(representation.getProperties().get("someBool"), true);
  }
}