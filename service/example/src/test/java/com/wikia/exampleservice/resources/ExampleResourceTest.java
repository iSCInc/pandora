package com.wikia.exampleservice.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikia.exampleservice.service.helloworld.HelloWorldService;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import io.dropwizard.testing.junit.ResourceTestRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExampleResourceTest {

  private final HelloWorldService helloWorldServiceMock = mock(
      HelloWorldService.class
  );

  @Rule
  public final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new ExampleResource(helloWorldServiceMock)).build();

  @Test
  public void getWelcomeMessageSuccess() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    when(helloWorldServiceMock.getWelcomeMessage("John")).thenReturn("Welcome John");

    String response = resources.client()
        .target("/HelloWorld/John")
        .request()
        .get(String.class);

    String greetings = mapper.readTree(response).get("properties").get("Greetings").asText();
    assertEquals("Welcome John", greetings);

    verify(helloWorldServiceMock).getWelcomeMessage("John");
  }

  @Test
  public void getSimplePojoSuccess() throws IOException {
    ObjectMapper mapper = new ObjectMapper();

    String response = resources.client()
        .target("/SimplePojo/12/John/true")
        .request()
        .get(String.class);

    JsonNode node = mapper.readTree(response).get("properties");
    assertEquals(12, node.get("id").asInt());
    assertEquals("John", node.get("someString").asText());
    assertEquals(true, node.get("someBool").asBoolean());
  }
}
