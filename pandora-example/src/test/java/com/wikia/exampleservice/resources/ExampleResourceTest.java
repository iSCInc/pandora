package com.wikia.exampleservice.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

import com.wikia.pandora.test.FastTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;

@Category(FastTest.class)
public class ExampleResourceTest {

  @Test
  public void testGetHelloWorld() throws Exception {
    RepresentationFactory representationFactory = new StandardRepresentationFactory();
    ExampleResource resource = new ExampleResource(representationFactory);
    resource.setGreetingsWord("Hi in Tests");
    Representation representation = resource.getHelloWorld("John");
    assertEquals(representation.getProperties().get("Greetings"), "Hi in Tests John");
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
