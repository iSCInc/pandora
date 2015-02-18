package com.wikia.mobileconfig.resources;

import com.wikia.mobileconfig.service.ImageService;
import com.wikia.pandora.test.FastTest;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.core.Response;

import io.dropwizard.testing.junit.ResourceTestRule;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link com.wikia.mobileconfig.resources.MobileConfigResource}.
 */
@Category(FastTest.class)
public class ImageResourceTest {

  private static final ImageService imageServiceMock = mock(
      ImageService.class
  );

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new ImageResource(imageServiceMock)).build();

  @Test
  public void getMobileImage_notFound() throws IOException {
    when(imageServiceMock.getImage("NonExistentImage.png"))
        .thenReturn(Optional.empty());

    Response response = resources.client()
      .target("/images/NonExistentImage.png")
      .request()
      .get();
    String responseData = response.readEntity(String.class);

    assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    assertThat(responseData).contains("NonExistentImage.png could not be found");

    verify(imageServiceMock).getImage("NonExistentImage.png");
    reset(imageServiceMock);
  }

  @Test
  public void getMobileApplicationConfigSuccess() throws IOException {
    Optional<byte[]> imgResponse = Optional.of(new byte[]{1});
    when(imageServiceMock.getImage("TestImage.png"))
        .thenReturn(imgResponse);

    Response response = resources.client()
      .target("/images/TestImage.png")
      .request()
      .get();

    byte[] responseData = response.readEntity(byte[].class);

    assertThat(responseData).isEqualTo(imgResponse.get());

    verify(imageServiceMock).getImage("TestImage.png");
    reset(imageServiceMock);
  }
}
