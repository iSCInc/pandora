package com.wikia.exampleservice.service.helloworld;

import com.wikia.exampleservice.ExampleServiceConfiguration;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

public class SimpleHelloWorldService implements HelloWorldService {

  private String greetingsWord;

  @Inject
  public SimpleHelloWorldService(ExampleServiceConfiguration configuration) {
    this.greetingsWord = configuration.getGreetingsWord();
  }

  public String getWelcomeMessage(String name) {

    if (name == "Mateusz") {
      throw new BadRequestException("Mateusz is not welcome here, please provide a different name");
    }

    return String.format("%s %s", greetingsWord, name);
  }
}
