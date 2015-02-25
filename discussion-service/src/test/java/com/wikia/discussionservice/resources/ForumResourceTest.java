package com.wikia.discussionservice.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.Forums;
import com.wikia.discussionservice.services.ForumService;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ForumResourceTest {

  private static final ForumService forumService = mock(ForumService.class);
  private static final RepresentationFactory representationFactory = 
      new StandardRepresentationFactory();

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new ForumResource(representationFactory, forumService))
      .build();

  private Forums firstOffsetForums;
  private Forums secondOffsetForums;

  @Before
  public void setup() {
    // reset the mock because of the static nature of the ResourceTestRule
    reset(forumService);
    
    firstOffsetForums = new Forums();
    firstOffsetForums.setOffset(1);
    firstOffsetForums.setTotal(100);
    firstOffsetForums.setLimit(10);
    firstOffsetForums.setForums(createForumList(1, 10));
    
    when(forumService.getForums(eq(1), eq(10)))
        .thenReturn(Optional.of(firstOffsetForums));

    secondOffsetForums = new Forums();
    secondOffsetForums.setOffset(2);
    secondOffsetForums.setTotal(100);
    secondOffsetForums.setLimit(10);
    secondOffsetForums.setForums(createForumList(2, 10));
    
    when(forumService.getForums(eq(2), eq(10)))
      .thenReturn(Optional.of(secondOffsetForums));
  }

  private List<Forum> createForumList(int offset, int limit) {
    List<Forum> forumList = new ArrayList<>();

    int start = offset == 1
        ? 1
        : (limit * offset) - (limit - 1);

    IntStream.range(start, limit+1).forEach(
        i -> {
          Forum forum = new Forum(i, String.format("Forum: %s", i), new ArrayList<>());
          forumList.add(forum);
        }
    );

    return forumList;
  }

  @Test
  public void testDefaultGetForums() {
    Response response = resources.client().target("/forums").request().get();
    assertThat(response.getStatus()).isEqualTo(200);
    verify(forumService).getForums(1, 10);
  }

  @Test
  public void testFirstOffsetGetForums() {
    Response response = resources.client().target("/forums?offset=1&limit=10").request().get();
    assertThat(response.getStatus()).isEqualTo(200);
    verify(forumService).getForums(1, 10);
  }

  @Test
  public void testSecondOffsetGetForums() {
    Response response = resources.client().target("/forums?offset=2&limit=10").request().get();
    assertThat(response.getStatus()).isEqualTo(200);
    verify(forumService).getForums(2, 10);
  }

  @Test
  public void testNegativeOffsetGetForums() {
    try {
      resources.client().target("/forums?offset=0&limit=10").request().get();
      fail("Excepted to fail because offset less than 1 is not allowed");
    } catch(ProcessingException pe) {
      // expected exception
      assertThat(pe.getCause()).isInstanceOf(IllegalArgumentException.class);
    }
    verifyZeroInteractions(forumService);
  }

  @Test
  public void testLessThanOneLimitGetForums() {
    try {
      resources.client().target("/forums?offset=1&limit=0").request().get();
      fail("Excepted to fail because limit less than 1 is not allowed");
    } catch(ProcessingException pe) {
      // expected exception
      assertThat(pe.getCause()).isInstanceOf(IllegalArgumentException.class);
    }
    verifyZeroInteractions(forumService);
  }

}
