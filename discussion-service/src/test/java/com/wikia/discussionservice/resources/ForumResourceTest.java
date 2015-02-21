package com.wikia.discussionservice.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.Forums;
import com.wikia.discussionservice.services.ForumService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ForumResourceTest {

  private static final ForumService forumService = mock(ForumService.class);
  private static final RepresentationFactory representationFactory =
      mock(StandardRepresentationFactory.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new ForumResource(representationFactory, forumService))
      .build();

  private Forums firstOffsetForums;
  private Forums secondOffsetForums;

  @Before
  public void setup() {
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
    
//    when(forumService.getForums(1, 10))
//      .thenReturn(Optional.of(secondOffsetForums));
    reset(forumService);
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
  public void testDefaultGetForums(){
//    assertThat(resources.client().target("/forums").request().get(Forums.class))
//        .isEqualTo(firstOffsetForums);
//
//    verify(forumService).getForums(1, 10);
  }

//  @Test
  public void testFirstOffsetGetForums() {
    assertThat(resources.client().invocation(Link.valueOf("/forums?offset=1&limit=10"))
        .get(Forums.class))
        .isEqualTo(firstOffsetForums);

    verify(forumService).getForums(1, 10);
  }

//  @Test
  public void testNegativeOffsetGetForums() {
    try {
      assertThat(resources.client().invocation(Link.valueOf("/forums?offset=0&limit=10"))
          .get(Forums.class))
          .isEqualTo(firstOffsetForums);
    } catch(IllegalArgumentException iae) {
      // expected
      verify(forumService).getForums(1, 10);
    }
  }

}
