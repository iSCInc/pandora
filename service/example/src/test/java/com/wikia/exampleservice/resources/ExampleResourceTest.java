package com.wikia.exampleservice.resources;

import static org.junit.Assert.assertEquals;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import org.junit.Test;

public class ExampleResourceTest {

  @Test
  public void testGetHelloWorld() {
    RepresentationFactory representationFactory = new StandardRepresentationFactory();
    ExampleResource resource = new ExampleResource(representationFactory);
    resource.setGreetingsWord("Hi in Tests");
    Representation representation = resource.getHelloWorld("John");
    assertEquals(representation.getProperties().get("Greetings"), "Hi in Tests John");
  }

  @Test
  public void testGetSimplePojo() {
    RepresentationFactory representationFactory = new StandardRepresentationFactory();
    ExampleResource resource = new ExampleResource(representationFactory);
    Representation representation = resource.getSimplePojo(7, "John", true);
    assertEquals(representation.getProperties().get("id"), 7);
    assertEquals(representation.getProperties().get("someString"), "John");
    assertEquals(representation.getProperties().get("someBool"), true);
  }
}
