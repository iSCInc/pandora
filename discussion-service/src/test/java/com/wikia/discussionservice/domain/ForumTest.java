package com.wikia.discussionservice.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;


public class ForumTest {
  
  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test(dataProvider = "fixtureAndExpectedParams")
  public void serializesAnddeserializesFromJSON(String fixtureFile, int id, String forumName, List<Forum> children,
                                                List<ForumThread> threads) throws Exception {
    final Forum actualForum = new Forum(id, forumName, children, threads);
    final Forum fixtureForum = MAPPER.readValue(fixture(fixtureFile), Forum.class);
    
    // TestNG doesn't compare the JSON in a normalized format
    // so there are false negatives
    assertThat(actualForum).isEqualTo(fixtureForum);
    assertThat(MAPPER.writeValueAsString(actualForum))
        .isEqualTo(MAPPER.writeValueAsString(fixtureForum));
  }

  @DataProvider
  public Object[][] fixtureAndExpectedParams() {
    List<Forum> children = new ArrayList<> (
        Arrays.asList(
            new Forum(3, "child 1", new ArrayList<>(), new ArrayList<>()),
            new Forum(4, "child 2", new ArrayList<>(), new ArrayList<>())
        )
    );

    return new Object[][]{
        {"fixtures/forumWithoutChildren.json", 1, "Top Level", new ArrayList<>(), new ArrayList<>()},
        {"fixtures/forumWithChildren.json", 1, "Forum With Children", children, new ArrayList<>()},
      };
  }
}
