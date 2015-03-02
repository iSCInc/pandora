package com.wikia.discussionservice.resources;

import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.wikia.discussionservice.domain.Forum;
import com.wikia.discussionservice.domain.ForumRoot;
import com.wikia.discussionservice.mappers.*;
import com.wikia.discussionservice.services.ForumService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
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

  private static final PostRepresentationMapper postRepresentationMapper =
      new HALPostRepresentationMapper(representationFactory);

  
  private static final ThreadRepresentationMapper threadRepresentationMapper =
      new HALThreadRepresentationMapper(representationFactory, postRepresentationMapper);

  private static final ForumRepresentationMapper forumRepresentationMapper =
      new HALForumRepresentationMapper(representationFactory, threadRepresentationMapper);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new ForumResource(forumService, forumRepresentationMapper))
      .build();

  private ForumRoot firstOffsetForumRoot;
  private ForumRoot secondOffsetForumRoot;

  @Before
  public void setup() {
    // reset the mock because of the static nature of the ResourceTestRule
    reset(forumService);
    
    firstOffsetForumRoot = new ForumRoot();
    firstOffsetForumRoot.setForums(createForumList(1, 10));
    
    when(forumService.getForums(anyInt())).thenReturn(Optional.of(firstOffsetForumRoot));
  }

  private List<Forum> createForumList(int offset, int limit) {
    List<Forum> forumList = new ArrayList<>();

    int start = offset == 1
        ? 1
        : (limit * offset) - (limit - 1);

    IntStream.range(start, limit+1).forEach(
        i -> {
          Forum forum = 
              new Forum(i, 1, String.format("Forum: %s", i), new ArrayList<>(), new ArrayList<>());
          forumList.add(forum);
        }
    );

    return forumList;
  }

  @Test
  public void testDefaultGetForums() {
    Response response = resources.client().target("/1/forums").request().get();
    assertThat(response.getStatus()).isEqualTo(200);
    verify(forumService).getForums(1);
  }
}
