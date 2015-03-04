package com.wikia.mobileconfig.resources;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wikia.mobileconfig.service.ImageService;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.core.Response;

/**
 * Unit tests for {@link com.wikia.mobileconfig.resources.MobileConfigResource}.
 */
public class ImageResourceTest {

  private static final ImageService IMAGE_SERVICE_MOCK = mock(
      ImageService.class
  );

  @ClassRule
  public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
      .addResource(new ImageResource(IMAGE_SERVICE_MOCK)).build();

  @Test
  public void getMobileImage_notFound() throws IOException {
    when(IMAGE_SERVICE_MOCK.getImage("NonExistentImage.png"))
        .thenReturn(Optional.empty());

    Response response = RESOURCES.client()
      .target("/images/NonExistentImage.png")
      .request()
      .get();
    String responseData = response.readEntity(String.class);

    assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    assertThat(responseData).contains("NonExistentImage.png could not be found");

    verify(IMAGE_SERVICE_MOCK).getImage("NonExistentImage.png");
    reset(IMAGE_SERVICE_MOCK);
  }

  @Test
  public void getMobileApplicationConfigSuccess() throws IOException {
    Optional<byte[]> imgResponse = Optional.of(new byte[]{1});
    when(IMAGE_SERVICE_MOCK.getImage("TestImage.png"))
        .thenReturn(imgResponse);

    Response response = RESOURCES.client()
      .target("/images/TestImage.png")
      .request()
      .get();

    byte[] responseData = response.readEntity(byte[].class);

    assertThat(responseData).isEqualTo(imgResponse.get());

    verify(IMAGE_SERVICE_MOCK).getImage("TestImage.png");
    reset(IMAGE_SERVICE_MOCK);
  }
}
